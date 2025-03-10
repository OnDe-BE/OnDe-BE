package com.ott.onde.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //CORS 허용
                .allowedOrigins("http://localhost:8080", "http://localhost:3000", "https://localhost:3000", "https://localhost:8080","https://ondemandia.com","https://www.ondemandia.com", "https://api.ondemandia.com", "http://api.ondemandia.com")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .exposedHeaders("Set-Cookie") // 쿠키가 클라이언트에서 접근 가능하도록 설정
                .maxAge(3600); // Preflight 요청 결과를 1시간 동안 캐싱
    }
}
