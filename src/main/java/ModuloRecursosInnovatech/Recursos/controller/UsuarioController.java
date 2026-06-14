package ModuloRecursosInnovatech.Recursos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ModuloRecursosInnovatech.Recursos.dto.AsignacionTareaExternaDTO;
import ModuloRecursosInnovatech.Recursos.dto.ProyectoExternoDTO;
import ModuloRecursosInnovatech.Recursos.dto.UsuarioDTO;
import ModuloRecursosInnovatech.Recursos.model.Usuario;
import ModuloRecursosInnovatech.Recursos.service.ProyectoClientService;
import ModuloRecursosInnovatech.Recursos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listado de usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuario();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios); 
    }

    //Nuevo endpoint para obtener los datos del usuario que está logueado.
    @GetMapping("/me")
    @Operation(summary = "Obtener mis datos de usuario (basado en el token)")
    public ResponseEntity<Usuario> getMyInfo(@RequestHeader("X-User-UID") String uid) {
        Usuario usuario = usuarioService.getUsuarioByUidFirebase(uid);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

     //Test para ver si el token pasa del apigateway al backend
    @GetMapping("/test")
    @Operation(summary = "Prueba de recepción de UID desde el Gateway")
    public ResponseEntity<String> test(@RequestHeader(value = "X-User-UID") String uid) {
        return ResponseEntity.ok("Éxito: El microservicio recibió el UID " + uid + " desde el Gateway.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario); 
    }

    @PostMapping
    @Operation(summary = "Para ingresar un usuario/trabajador nuevo vinculado a su Firebase UID")
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO) { 
        
        // Usamos el nuevo metodo del service que procesa el DTO y busca las relaciones reales
        Usuario createdUsuario = usuarioService.guardarDesdeDTO(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cambiar un usuario")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Integer id, 
            @RequestBody UsuarioDTO usuarioDTO) { // <-- Aquí cambiamos de Usuario a UsuarioDTO
        
        Usuario existente = usuarioService.getUsuarioById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }     
        
        // Llamamos al nuevo método seguro del Service
        Usuario updatedUsuario = usuarioService.actualizarDesdeDTO(id, usuarioDTO);
        return ResponseEntity.ok(updatedUsuario); 
    }

    @PatchMapping("/firebase/{uid}/activar")
    @Operation(summary = "Activa el estado del usuario usando su UID de Firebase")
    public ResponseEntity<Usuario> activarUsuario(
            @PathVariable String uid,
            @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioActualizado = usuarioService.activarUsuarioPorUid(uid, usuarioDTO.getActivo());
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        if (usuarioService.getUsuarioById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();  
    }

    @Autowired
    private ProyectoClientService proyectoClientService;

    // Endpoint para proyectos a cargo
    @GetMapping("/me/proyectos")
    @Operation(summary = "Obtener proyectos donde el usuario logueado es jefe")
    public ResponseEntity<List<ProyectoExternoDTO>> getMisProyectos(@RequestHeader("X-User-UID") String uid) {
        Usuario usuario = usuarioService.getUsuarioByUidFirebase(uid);
        if (usuario == null) return ResponseEntity.notFound().build();

        // Le pasamos el UID largo Y el ID numérico convertido a String
        String idNumerico = String.valueOf(usuario.getId());
        List<ProyectoExternoDTO> misProyectos = proyectoClientService.obtenerProyectosPorJefe(uid, idNumerico);
        
        return ResponseEntity.ok(misProyectos);
    }

    @GetMapping("/me/tareas")
    @Operation(summary = "Obtener tareas asignadas al usuario logueado")
    public ResponseEntity<List<AsignacionTareaExternaDTO>> getMisTareas(@RequestHeader("X-User-UID") String uid) {
        Usuario usuario = usuarioService.getUsuarioByUidFirebase(uid);
        if (usuario == null) return ResponseEntity.notFound().build();

        // Le pasamos el UID largo Y el ID numérico convertido a String
        String idNumerico = String.valueOf(usuario.getId());
        List<AsignacionTareaExternaDTO> misTareas = proyectoClientService.obtenerTareasPorUsuario(uid, idNumerico);
        
        return ResponseEntity.ok(misTareas);
    }

}