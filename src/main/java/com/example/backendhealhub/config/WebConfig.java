package com.example.backendhealhub.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Update allowedOrigins to your frontend URL
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // Allow credentials such as cookies, authorization headers, etc.
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the URL path "/images/**" to the physical directory path "file:///path_to_user-storage/"
        registry.addResourceHandler("/images/**").addResourceLocations("file:///C:/Users/Admin/Desktop/BackEndHealHub19/user-storage/");

    }
}
