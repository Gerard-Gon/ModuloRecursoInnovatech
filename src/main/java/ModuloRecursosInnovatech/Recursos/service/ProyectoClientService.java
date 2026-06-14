package ModuloRecursosInnovatech.Recursos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ModuloRecursosInnovatech.Recursos.dto.ProyectoExternoDTO;
import ModuloRecursosInnovatech.Recursos.dto.AsignacionTareaExternaDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoClientService {

    private final WebClient webClient;

    // Agregamos el mismo secreto que usó tu compañero
    @Value("${GATEWAY_SECRET:local_test_back}")
    private String secretoCompartido;

    @Autowired
    public ProyectoClientService(
            WebClient.Builder webClientBuilder,
            @Value("${PROYECTOS_SERVICE_URL:https://modulogestionproyecto.onrender.com/api/v1}") String baseUrl) {
        
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    // Ahora recibe el UID y el ID numérico convertido a texto
    public List<ProyectoExternoDTO> obtenerProyectosPorJefe(String uidFirebase, String idDb) {
        try {
            List<ProyectoExternoDTO> todosLosProyectos = webClient.get()
                    .uri("/proyectos")
                    .header("X-Gateway-Secret", secretoCompartido)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ProyectoExternoDTO>>() {})
                    .doOnError(error -> System.err.println("ERROR WEBCLIENT PROYECTOS: " + error.getMessage()))
                    .block();

            if (todosLosProyectos != null) {
                return todosLosProyectos.stream()
                        // Acepta si coincide con el Firebase UID O con el ID de la base de datos
                        .filter(p -> p.getJefeId() != null && 
                                    (p.getJefeId().equals(uidFirebase) || p.getJefeId().equals(idDb)))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("El microservicio de proyectos no responde: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    // Ahora recibe el UID y el ID numérico convertido a texto
    public List<AsignacionTareaExternaDTO> obtenerTareasPorUsuario(String uidFirebase, String idDb) {
        try {
            List<AsignacionTareaExternaDTO> todasLasAsignaciones = webClient.get()
                    .uri("/asignaciones-tareas")
                    .header("X-Gateway-Secret", secretoCompartido)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<AsignacionTareaExternaDTO>>() {})
                    .doOnError(error -> System.err.println("ERROR WEBCLIENT TAREAS: " + error.getMessage()))
                    .block();

            if (todasLasAsignaciones != null) {
                return todasLasAsignaciones.stream()
                        // Acepta si coincide con el Firebase UID O con el ID de la base de datos
                        .filter(a -> a.getUsuarioId() != null && 
                                    (a.getUsuarioId().equals(uidFirebase) || a.getUsuarioId().equals(idDb)) &&
                                    Boolean.TRUE.equals(a.getEstado()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.err.println("El microservicio de tareas no responde: " + e.getMessage());
        }
        return Collections.emptyList();
    }
    
}