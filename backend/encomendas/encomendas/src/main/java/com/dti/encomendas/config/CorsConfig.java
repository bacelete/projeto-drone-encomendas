package com.dti.encomendas.config;

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
                registry.addMapping("/drones/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/entregas/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/pedidos/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/relatorio/**").allowedOrigins("http://localhost:5173");
            }
        };
    }
}
