package com.example.libertyformapiserver.config;

import com.example.libertyformapiserver.interceptor.AuthenticationInterceptor;
import com.example.libertyformapiserver.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
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
        registry.addInterceptor(new AuthenticationInterceptor(jwtService, objectMapper))
                .order(0) // order는 인터셉터의 우선 순위를 정의한다.
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs");
    }
}
