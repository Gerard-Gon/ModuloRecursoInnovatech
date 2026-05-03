package ModuloRecursosInnovatech.Recursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ModuloRecursosInnovatech.Recursos.model.Cargo;
import ModuloRecursosInnovatech.Recursos.repository.CargoRepository;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public Cargo getCargoById(Integer id) {
        return cargoRepository.findById(id).orElse(null);
    }

    public Cargo saveCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public void deleteCargo(Integer id) {
        cargoRepository.deleteById(id);
    }


    
}
