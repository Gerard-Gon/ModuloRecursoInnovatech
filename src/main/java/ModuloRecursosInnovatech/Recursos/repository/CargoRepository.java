package ModuloRecursosInnovatech.Recursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ModuloRecursosInnovatech.Recursos.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    
}
