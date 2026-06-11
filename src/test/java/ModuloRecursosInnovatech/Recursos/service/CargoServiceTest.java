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

import ModuloRecursosInnovatech.Recursos.model.Cargo;
import ModuloRecursosInnovatech.Recursos.repository.CargoRepository;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {

    @Mock
    private CargoRepository cargoRepository;

    @InjectMocks
    private CargoService cargoService;

    private Cargo cargoFalso;

    @BeforeEach
    void setUp() {
        cargoFalso = new Cargo();
        cargoFalso.setId(1);
        cargoFalso.setNombreCargo("Desarrollador Backend");
    }

    @Test
    void testGetCargoById_Exitoso() {
        when(cargoRepository.findById(1)).thenReturn(Optional.of(cargoFalso));

        Cargo resultado = cargoService.getCargoById(1);

        assertNotNull(resultado);
        assertEquals("Desarrollador Backend", resultado.getNombreCargo());
        verify(cargoRepository, times(1)).findById(1);
    }

    @Test
    void testSaveCargo_Exitoso() {
        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargoFalso);

        Cargo resultado = cargoService.saveCargo(cargoFalso);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Desarrollador Backend", resultado.getNombreCargo());
        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }
}