package ModuloRecursosInnovatech.Recursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ModuloRecursosInnovatech.Recursos.model.Cargo;
import ModuloRecursosInnovatech.Recursos.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/cargos")

public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    @Operation(summary = "Listado de cargos de los desarrolladores")
    public ResponseEntity<List<Cargo>> getAllCargos() {
        List<Cargo> cargos = cargoService.getAllCargo();
        if (cargos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cargos); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "LLama un cargo en especifico de los desarrolladores")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Integer id) {
        Cargo cargo = cargoService.getCargoById(id);
        if (cargo == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(cargo); 
    }

    @PostMapping
    @Operation(summary = "Para ingresar un cargo nuevo")
    public ResponseEntity<Cargo> createCargo(@RequestBody Cargo cargo) {
        
        cargo.setId(null); 
        Cargo createdCargo = cargoService.saveCargo(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCargo);
    }



    @PutMapping("/{id}")
    @Operation(summary = "Cambiar un cargo")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Integer id, @RequestBody Cargo cargo) {
        // Verificamos si existe antes de intentar actualizar
        if (cargoService.getCargoById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        cargo.setId(id);
        Cargo updatedCargo = cargoService.saveCargo(cargo);
        return ResponseEntity.ok(updatedCargo); 
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un Cargo")
    public ResponseEntity<Void> deleteCargo(@PathVariable Integer id) {
        cargoService.deleteCargo(id);
        return ResponseEntity.noContent().build();  
    }

    
    
}
