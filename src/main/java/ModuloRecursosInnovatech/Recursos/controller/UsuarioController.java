package ModuloRecursosInnovatech.Recursos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ModuloRecursosInnovatech.Recursos.model.Usuario;
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

    /**
     * Nuevo endpoint para obtener los datos del usuario que está logueado.
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener mis datos de usuario (basado en el token)")
    public ResponseEntity<Usuario> getMyInfo(@RequestHeader("X-User-UID") String uid) {
        Usuario usuario = usuarioService.getUsuarioByUidFirebase(uid);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

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
    @Operation(summary = "Para ingresar un usuario nuevo vinculado a su Firebase UID")
    public ResponseEntity<Usuario> createUsuario(
            @RequestBody Usuario usuario, 
            @RequestHeader("X-User-UID") String uid) { // Capturamos el UID del Gateway
        
        usuario.setId(null); 
        // Usamos el nuevo método del service que vincula el UID
        Usuario createdUsuario = usuarioService.saveUsuario(usuario, uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cambiar un usuario")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Integer id, 
            @RequestBody Usuario usuario,
            @RequestHeader("X-User-UID") String uid) {
        
        Usuario existente = usuarioService.getUsuarioById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Opcional: Podrías validar que el 'uid' del token sea el mismo que el 'uidFirebase' del registro
        // para que un usuario no pueda editar a otro.[cite: 2]
        
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.saveUsuario(usuario, uid);
        return ResponseEntity.ok(updatedUsuario); 
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
}