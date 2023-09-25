package gugunava.danil.usermanagementservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management Service")
                        .description("RESTful User Management Service with Token-Based Authorization, Dockerized PostgreSQL Database, Setup Scripts, Spring Boot, Spring Cache and Login Endpoint")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("user_auth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}