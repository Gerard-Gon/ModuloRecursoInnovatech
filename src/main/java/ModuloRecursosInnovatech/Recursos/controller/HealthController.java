package ModuloRecursosInnovatech.Recursos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController{

    //Aca esta el controller para mantener la api en render "viva"  
    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

}