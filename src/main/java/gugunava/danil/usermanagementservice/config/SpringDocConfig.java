package gugunava.danil.usermanagementservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    public static final String BEARER_AUTHENTICATION = "Bearer Authentication";

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(BEARER_AUTHENTICATION, createAPIKeyScheme()))
                .info(new Info()
                        .title("User Management Service")
                        .description("RESTful User Management Service with Token-Based Authorization, Dockerized PostgreSQL Database, Setup Scripts, Spring Boot, Spring Cache and Login Endpoint")
                        .version("1.0.0"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}