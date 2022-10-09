package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.login.post.PostLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginRes;
import com.example.libertyformapiserver.service.LoginService;
import com.example.libertyformapiserver.utils.jwt.NoIntercept;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    // 일반 로그인
    @PostMapping
    @NoIntercept
    public BaseResponse<PostLoginRes> login(@Validated @RequestBody PostLoginReq dto){
        PostLoginRes postLoginRes = loginService.login(dto);
        return new BaseResponse<>(postLoginRes);
    }
}
