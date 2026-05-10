package ModuloRecursosInnovatech.Recursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    // Configuracion de swagger (principalmente es nombre-version-descripcion)
   @Bean
   public OpenAPI customOpenAPI(){
       return new OpenAPI().info(
           new Info()
           .title("API INNOVATECH")
           .version("0.1")
           .description("Api para Innovatech solutions chile ")
       );
   }
}