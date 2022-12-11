package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.login.kakao.post.PostKakaoLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginRes;
import com.example.libertyformapiserver.service.LoginService;
import com.example.libertyformapiserver.jwt.NoIntercept;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
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
            @ApiResponse(code = 2005, message = "중복된 이메일 주소입니다."),
            @ApiResponse(code = 2006, message = "존재하지 않는 이메일 주소입니다.")
    })
    @PostMapping
    @NoIntercept
    public BaseResponse<PostLoginRes> login(@Validated @RequestBody PostLoginReq dto){
        PostLoginRes postLoginRes = loginService.login(dto);
        log.info("Login : {}", postLoginRes.getEmail());

        return new BaseResponse<>(postLoginRes);
    }

    // 카카오 로그인
    @ApiOperation(
            value = "카카오 로그인",
            notes = "1. 카카오 로그인 기능, 카카오 서버에서 accessToken 값을 받아서 넘겨주면 된다." +
                    "\n 2. 로그인 할 때 회원 등록이 안되어있으면 회원가입 후 로그인이 진행된다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2004, message = "비밀번호 확인란이 일치하지 않습니다."),
            @ApiResponse(code = 2005, message = "중복된 이메일 주소입니다."),
            @ApiResponse(code = 2006, message = "존재하지 않는 이메일 주소입니다.")
    })
    @PostMapping("/kakao")
    @NoIntercept
    public BaseResponse<PostLoginRes> kakaoLogin(@RequestBody PostKakaoLoginReq dto){
        PostLoginRes postLoginRes = loginService.kakaoLogin(dto);
        log.info("Kakao Login : {}", postLoginRes.getEmail());

        return new BaseResponse<>(postLoginRes);
    }

    // 카카오 로그인 후 accessToken 받기 (테스트 용)
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2004, message = "비밀번호 확인란이 일치하지 않습니다."),
            @ApiResponse(code = 2005, message = "중복된 이메일 주소입니다.")
    })
    @GetMapping("/kakao/accessToken")
    @NoIntercept
    public void kakaoCallback(@RequestParam String code){
        System.out.println("Kakao accessCode:" + code);
        loginService.getKakaoAccessToken(code);
    }
}
