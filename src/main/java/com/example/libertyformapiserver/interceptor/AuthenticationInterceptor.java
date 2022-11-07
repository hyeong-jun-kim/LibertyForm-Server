package com.example.libertyformapiserver.interceptor;

import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.jwt.JwtService;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.jwt.ResponseIntercept;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // @NoIntercept -> not apply intercept
        boolean check = checkNoIntercept(handler, NoIntercept.class);
        if(check) return true;

        getJwtToken(request, handler);

        return true;
    }

    private boolean checkNoIntercept(Object handler, Class c){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(c) != null){
            return true;
        }
            return false;
    }

    private boolean checkResponseIntercept(Object handler, Class c){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(c) != null){
            return true;
        }
        return false;
    }

    private void getJwtToken(HttpServletRequest request, Object handler){
        LinkedHashMap jwtInfo;

        // ResponseIntercept는 Jwt가 null이여도 받아옴
        if(checkResponseIntercept(handler, ResponseIntercept.class)){
            jwtInfo = jwtService.getResponseJwtInfo();
        }else{
            jwtInfo = jwtService.getJwtInfo();
        }
        if(jwtInfo == null)
            return;

        JwtInfo convertJwtInfo = objectMapper.convertValue(jwtInfo, JwtInfo.class);
        request.setAttribute("jwtInfo", convertJwtInfo);
    }
}
