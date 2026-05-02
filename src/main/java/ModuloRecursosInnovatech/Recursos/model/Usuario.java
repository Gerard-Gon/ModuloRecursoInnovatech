package ModuloRecursosInnovatech.Recursos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "uid_firebase", nullable = false, unique = true, length = 128)
    private String uidFirebase;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    
}
