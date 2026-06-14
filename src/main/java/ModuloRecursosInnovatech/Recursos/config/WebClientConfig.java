package ModuloRecursosInnovatech.Recursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Expone el Builder para que cada servicio configure su propia baseUrl
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
