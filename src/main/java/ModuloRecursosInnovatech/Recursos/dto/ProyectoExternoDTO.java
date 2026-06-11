package ModuloRecursosInnovatech.Recursos.dto;

import lombok.Data;

@Data 
public class ProyectoExternoDTO {
    private Integer id;
    private String nombreProyecto;
    private String descripcionProyecto;
    private String jefeId;
    private Boolean activo;
}