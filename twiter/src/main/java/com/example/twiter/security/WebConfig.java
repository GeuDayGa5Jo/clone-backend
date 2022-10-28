package com.example.twiter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry reg) {
        reg
                // 전부 허용
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                .allowedMethods("GET","POST","PUT","DELETE")
                .exposedHeaders("Authorization","RefreshToken")
                .allowCredentials(true);
    }
}