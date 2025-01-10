package com.emakers.api_back.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class cors implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**") // Permitir CORS para todos os endpoints
                .allowedOrigins("http://localhost:8080") // Permitir a origem especificada
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permitir todos os cabeçalhos
                .allowCredentials(true) // Permitir o envio de credenciais
                .maxAge(3600); // Tempo de cache para as respostas prévias de CORS (1 hora)
    }
}