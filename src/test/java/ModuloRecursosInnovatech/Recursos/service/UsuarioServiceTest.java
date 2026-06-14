package ModuloRecursosInnovatech.Recursos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ModuloRecursosInnovatech.Recursos.dto.UsuarioDTO;
import ModuloRecursosInnovatech.Recursos.model.Cargo;
import ModuloRecursosInnovatech.Recursos.model.Categoria;
import ModuloRecursosInnovatech.Recursos.model.Usuario;
import ModuloRecursosInnovatech.Recursos.repository.CargoRepository;
import ModuloRecursosInnovatech.Recursos.repository.CategoriaRepository;
import ModuloRecursosInnovatech.Recursos.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTOFalso;
    private Usuario usuarioFalso;
    private Categoria categoriaFalsa;
    private Cargo cargoFalso;

    @BeforeEach
    void setUp() {
        categoriaFalsa = new Categoria();
        categoriaFalsa.setId(1);
        categoriaFalsa.setCategoria("Senior");

        cargoFalso = new Cargo();
        cargoFalso.setId(2);
        cargoFalso.setNombreCargo("Arquitecto de Software");

        // DTO de entrada
        usuarioDTOFalso = new UsuarioDTO();
        usuarioDTOFalso.setNombre("Gerardo Gonzales");
        usuarioDTOFalso.setEmail("gerardo@innovatech.cl");
        usuarioDTOFalso.setUidFirebase("firebase_uid_abc123");
        usuarioDTOFalso.setSueldo(2500000);
        usuarioDTOFalso.setCategoriaId(1);
        usuarioDTOFalso.setCargoId(2);
        usuarioDTOFalso.setActivo(true);

        // Modelo de salida esperado
        usuarioFalso = new Usuario();
        usuarioFalso.setId(10);
        usuarioFalso.setNombre("Gerardo Gonzales");
        usuarioFalso.setEmail("gerardo@innovatech.cl");
        usuarioFalso.setUidFirebase("firebase_uid_abc123");
        usuarioFalso.setSueldo(2500000);
        usuarioFalso.setCategoria(categoriaFalsa);
        usuarioFalso.setCargo(cargoFalso);
        usuarioFalso.setActivo(true);
    }

    @Test
    void testGuardarDesdeDTO_Exitoso() {
        // se simula que la categoría y el cargo existen en la base de datos
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaFalsa));
        when(cargoRepository.findById(2)).thenReturn(Optional.of(cargoFalso));
        
        // guardado simulado
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioFalso);

        Usuario resultado = usuarioService.guardarDesdeDTO(usuarioDTOFalso);

        assertNotNull(resultado);
        assertEquals("Gerardo Gonzales", resultado.getNombre());
        assertEquals("Senior", resultado.getCategoria().getCategoria());
        assertEquals("Arquitecto de Software", resultado.getCargo().getNombreCargo());
        
        verify(categoriaRepository, times(1)).findById(1);
        verify(cargoRepository, times(1)).findById(2);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testGuardarDesdeDTO_CategoriaNoExiste_LanzaExcepcion() {
        // Simulamos que la categoría NO existe
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.guardarDesdeDTO(usuarioDTOFalso);
        });

        assertEquals("Categoría no encontrada con ID: 1", exception.getMessage());
        // Verificamos que el guardado NUNCA se ejecute si falla la validación
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testGuardarDesdeDTO_CargoNoExiste_LanzaExcepcion() {
        // Simulamos que la categoría sí existe, pero el cargo NO
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaFalsa));
        when(cargoRepository.findById(2)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.guardarDesdeDTO(usuarioDTOFalso);
        });

        assertEquals("Cargo no encontrado con ID: 2", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testActivarUsuarioPorUid_Exitoso() {
        when(usuarioRepository.findByUidFirebase("firebase_uid_abc123")).thenReturn(Optional.of(usuarioFalso));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioFalso);

        Usuario resultado = usuarioService.activarUsuarioPorUid("firebase_uid_abc123", false);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findByUidFirebase("firebase_uid_abc123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}