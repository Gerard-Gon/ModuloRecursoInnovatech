package ModuloRecursosInnovatech.Recursos.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Categoria {
    private String categoria;
    private Integer id;
}
