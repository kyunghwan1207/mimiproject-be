package com.example.emart.config;

import com.example.emart.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        // Controller를 "/api/*" 로 만들것이기 때문에 해당 경로만 허용함.
        registry
                .addMapping("/api/**")
                .allowedOrigins("http://localhost:3000");
    }
}
