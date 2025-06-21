package org.example.costsplitbd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite solicitudes desde tu frontend
        config.addAllowedOrigin("http://localhost:4200");
        // Métodos HTTP permitidos
        config.addAllowedMethod("*");
        // Cabeceras permitidas
        config.addAllowedHeader("*");
        // Permite enviar cookies (importante si usas autenticación)
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}