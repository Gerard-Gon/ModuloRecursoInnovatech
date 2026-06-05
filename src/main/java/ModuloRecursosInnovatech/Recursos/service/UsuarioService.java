package ModuloRecursosInnovatech.Recursos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ModuloRecursosInnovatech.Recursos.dto.UsuarioDTO;
import ModuloRecursosInnovatech.Recursos.model.Cargo;
import ModuloRecursosInnovatech.Recursos.model.Categoria;
import ModuloRecursosInnovatech.Recursos.model.Usuario;
import ModuloRecursosInnovatech.Recursos.repository.CargoRepository;
import ModuloRecursosInnovatech.Recursos.repository.CategoriaRepository;
import ModuloRecursosInnovatech.Recursos.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final CargoRepository cargoRepository;

    public List<Usuario> getAllUsuario() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    //Nuevo método para buscar al usuario por su ID de Firebase.
    //Útil para validar quién está haciendo la petición.

    public Usuario getUsuarioByUidFirebase(String uid) {
        return usuarioRepository.findByUidFirebase(uid).orElse(null);
    }

    //Guardar usuario asegurando que el UID provenga del token validado.
    public Usuario saveUsuario(Usuario usuario, String uidFirebase) {
        usuario.setUidFirebase(uidFirebase); // Vinculamos forzosamente el UID real
        return usuarioRepository.save(usuario);
    }

    public Usuario guardarDesdeDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setUidFirebase(dto.getUidFirebase()); // El UID llega desde Firebase Front
        usuario.setSueldo(dto.getSueldo());

        // Buscamos las entidades reales en la BD usando los IDs que mandó el Frontend
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoriaId()));
        usuario.setCategoria(categoria);

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID: " + dto.getCargoId()));
        usuario.setCargo(cargo);

        return usuarioRepository.save(usuario);
    }

    // Método para actualizar usando el DTO (evita sobreescribir el UID y el Email)
    public Usuario actualizarDesdeDTO(Integer id, UsuarioDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setSueldo(dto.getSueldo());

        // Buscamos las entidades reales en la BD usando los IDs que mandó el Frontend
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoriaId()));
        usuarioExistente.setCategoria(categoria);

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID: " + dto.getCargoId()));
        usuarioExistente.setCargo(cargo);

        // Guardamos (nota que NO tocamos el email ni el uidFirebase)
        return usuarioRepository.save(usuarioExistente);
    }

    public Usuario cambiarEstadoLogeoPorUid(String uidFirebase, Boolean estado) {
        Usuario usuario = usuarioRepository.findByUidFirebase(uidFirebase)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con UID: " + uidFirebase));
        
        usuario.setLogeado(estado);
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}

