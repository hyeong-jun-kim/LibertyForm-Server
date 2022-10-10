package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.login.post.PostLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginRes;
import com.example.libertyformapiserver.service.LoginService;
import com.example.libertyformapiserver.utils.jwt.NoIntercept;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    // 일반 로그인
    @ApiOperation(
            value = "일반 로그인",
            notes = "일반 로그인 기능이다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2004, message = "비밀번호 확인란이 일치하지 않습니다."),
            @ApiResponse(code = 2005, message = "중복된 이메일 주소입니다.")
    })
    @PostMapping
    @NoIntercept
    public BaseResponse<PostLoginRes> login(@Validated @RequestBody PostLoginReq dto){
        PostLoginRes postLoginRes = loginService.login(dto);
        return new BaseResponse<>(postLoginRes);
    }

    // 카카오 로그인 후 accessToken 받기 (테스트 용)
    @GetMapping("/kakao/accessToken")
    @NoIntercept
    public void kakaoCallback(@RequestParam String code){
        System.out.println("Kakao accessCode:" + code);
        loginService.getKakaoAccessToken(code);
    }

    // 카카오 로그인
    @PostMapping("/kakao")
    @NoIntercept
    public void kakaoLogin(@RequestBody String accessToken){
        loginService.getKakaoUserInfo(accessToken);
        // TODO jwt 및 사용자 정보 반환
    }
}
