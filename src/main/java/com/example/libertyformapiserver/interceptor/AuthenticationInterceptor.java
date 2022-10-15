package com.example.libertyformapiserver.interceptor;

import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.controller.jwt.JwtService;
import com.example.libertyformapiserver.controller.jwt.NoIntercept;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // @NoIntercept -> not apply intercept
        boolean check = checkNoIntercept(handler, NoIntercept.class);
        if(check) return true;

        LinkedHashMap jwtInfo = jwtService.getJwtInfo();
        JwtInfo convertJwtInfo = objectMapper.convertValue(jwtInfo, JwtInfo.class);
        request.setAttribute("jwtInfo", convertJwtInfo);
        return true;
    }

    private boolean checkNoIntercept(Object handler, Class c){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(c) != null){
            return true;
        }
        return false;
    }
}
