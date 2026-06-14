package ModuloRecursosInnovatech.Recursos.dto;

import lombok.Data;

@Data
public class TareaExternaDTO {
    private Integer id;
    private String nombreTareas; 
    private String descripcionTareas;
    private String progreso;
    private ProyectoExternoDTO proyecto;
}