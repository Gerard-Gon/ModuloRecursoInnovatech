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

import ModuloRecursosInnovatech.Recursos.model.Categoria;
import ModuloRecursosInnovatech.Recursos.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoriaFalsa;

    @BeforeEach
    void setUp() {
        categoriaFalsa = new Categoria();
        categoriaFalsa.setId(1);
        categoriaFalsa.setCategoria("Senior");
    }

    @Test
    void testGetCategoriaById_Exitoso() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaFalsa));

        Categoria resultado = categoriaService.getCategoriaById(1);

        assertNotNull(resultado);
        assertEquals("Senior", resultado.getCategoria());
        verify(categoriaRepository, times(1)).findById(1);
    }

    @Test
    void testSaveCategoria_Exitoso() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaFalsa);

        Categoria resultado = categoriaService.saveCategoria(categoriaFalsa);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }
}