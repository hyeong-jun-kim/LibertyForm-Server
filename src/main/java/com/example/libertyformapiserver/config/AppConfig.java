package com.example.libertyformapiserver.config;

import com.example.libertyformapiserver.interceptor.AuthenticationInterceptor;
import com.example.libertyformapiserver.interceptor.LoggingInterceptor;
import com.example.libertyformapiserver.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private ObjectMapper objectMapper;

    // 인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        objectMapper = new ObjectMapper();

        // JWT 인터셉터 등록
        registry.addInterceptor(new AuthenticationInterceptor(jwtService, objectMapper))
                .order(0) // order는 인터셉터의 우선 순위를 정의한다.
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs");

        // Logging 인터셉터 등록
        registry.addInterceptor(new LoggingInterceptor())
                .order(1) // order는 인터셉터의 우선 순위를 정의한다.
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs");
    }

    // CORS wildcard
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
