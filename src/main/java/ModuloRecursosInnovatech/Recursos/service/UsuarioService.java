package ModuloRecursosInnovatech.Recursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloRecursosInnovatech.Recursos.model.Usuario;
import ModuloRecursosInnovatech.Recursos.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}

