package com.example.opcservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
