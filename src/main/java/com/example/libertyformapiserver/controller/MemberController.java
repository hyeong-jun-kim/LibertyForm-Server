package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.member.post.PostRegisterReq;
import com.example.libertyformapiserver.dto.member.post.PostRegisterRes;
import com.example.libertyformapiserver.service.MemberService;
import com.example.libertyformapiserver.utils.jwt.NoIntercept;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 가입
    @PostMapping
    @NoIntercept
    public BaseResponse<PostRegisterRes> registerMember(@Validated @RequestBody PostRegisterReq dto){
        PostRegisterRes postCreateMemberRes = memberService.registerMember(dto);
        return new BaseResponse<>(postCreateMemberRes);
    }
}
