package ModuloRecursosInnovatech.Recursos.dto;

import lombok.Data;

@Data
public class AsignacionTareaExternaDTO {
    private Integer id;
    private TareaExternaDTO tarea; 
    private String usuarioId; 
    private String fechaAsignacion; 
    private Boolean estado;
}