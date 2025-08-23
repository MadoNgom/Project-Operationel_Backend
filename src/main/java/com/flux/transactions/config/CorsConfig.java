package com.flux.transactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Applique les règles à tous les endpoints
                        .allowedOriginPatterns("*") // Autorise toutes les origines
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                        .allowedHeaders("*") // En-têtes autorisés
                        .allowCredentials(true) // Autorise les cookies et les informations d'identification
                        .maxAge(3600); // Cache les résultats preflight pendant 1 heure
            }
        };
    }
}