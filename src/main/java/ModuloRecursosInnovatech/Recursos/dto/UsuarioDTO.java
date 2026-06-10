package ModuloRecursosInnovatech.Recursos.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombre;
    private String email;
    private String uidFirebase;
    private Integer sueldo;
    private Integer categoriaId; 
    private Integer cargoId;     
    private Boolean activo;     
}