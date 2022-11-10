package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.dto.contact.post.PostContactReq;
import com.example.libertyformapiserver.dto.contact.post.PostContactRes;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.service.ContactService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    @ApiOperation(
            value = "설문 발송 대상자 추가하기",
            notes = "이메일, 관계, 이름을 입력해 설문 발송 대상자를 추가합니다. 만약에 해당 이메일이 설문지를 이용하고 있는 이메일이면 member에 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2017, message = "자기 자신의 이메일로는 등록하실 수 없습니다."),
            @ApiResponse(code = 2018, message = "이미 연락처에 등록된 이메일입니다.")
    })
    @PostMapping
    public PostContactRes createContact(@RequestBody @Validated PostContactReq postContactReq, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);
        PostContactRes postContactRes = contactService.createContact(postContactReq, JwtInfo.getMemberId(request));

        log.info("Create contact", "memberId: " + memberId, "| contact_email: " + postContactReq.getEmail());

        return postContactRes;
    }
}
