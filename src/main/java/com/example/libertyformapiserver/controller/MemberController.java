package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.member.post.PostRegisterReq;
import com.example.libertyformapiserver.dto.member.post.PostRegisterRes;
import com.example.libertyformapiserver.service.MemberService;
import com.example.libertyformapiserver.jwt.NoIntercept;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 가입
    @ApiOperation(
            value = "일반 회원가입",
            notes = "일반 회원가입을 통해서 사용자 정보를 등록할 수 있다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2006, message = "존재하지 않는 이메일입니다."),
            @ApiResponse(code = 2007, message = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(code = 2008, message = "삭제된 데이터입니다.")}
    )
    @PostMapping
    @NoIntercept
    public BaseResponse<PostRegisterRes> registerMember(@Validated @RequestBody PostRegisterReq dto){
        PostRegisterRes postCreateMemberRes = memberService.registerMember(dto);
        log.info("New Member : {}", postCreateMemberRes.getEmail());

        return new BaseResponse<>(postCreateMemberRes);
    }
}
