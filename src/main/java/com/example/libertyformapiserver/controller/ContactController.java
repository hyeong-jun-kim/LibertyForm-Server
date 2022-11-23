package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.contact.get.GetContactsRes;
import com.example.libertyformapiserver.dto.contact.get.GetPagingContactsRes;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactReq;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactRes;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.service.ContactService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.CONTACT_DELETE_SUCCESS;

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
    @PostMapping("/create")
    public BaseResponse<PostCreateContactRes> createContact(@RequestBody @Validated PostCreateContactReq postCreateContactReq, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);
        PostCreateContactRes postCreateContactRes = contactService.createContact(postCreateContactReq, JwtInfo.getMemberId(request));
        log.info("Create contact : {}", "memberId: - " + memberId, "| contact_email - " + postCreateContactReq.getEmail());

        return new BaseResponse<>(postCreateContactRes);
    }

    @ApiOperation(
            value = "설문 발송 대상자 불러오기 (Default)",
            notes = "자신의 설문 발송 대상자들을 불러옵니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2018, message = "존재하지 않는 연락처입니다.")
    })
    @GetMapping
    public BaseResponse<GetContactsRes> loadMyContacts(HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);
        GetContactsRes getContactsRes = contactService.getMyContacts(JwtInfo.getMemberId(request));
        log.info("Load contact : {}", "memberId - " + memberId);

        return new BaseResponse<>(getContactsRes);
    }

    @ApiOperation(
            value = "설문 발송 대상자 불러오기 (Paging)",
            notes = "페이징 처리된 자신의 설문 발송 대상자들을 불러옵니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2018, message = "존재하지 않는 연락처입니다."),
            @ApiResponse(code = 4004, message = "존재하지 않는 페이지입니다.")
    })
    @GetMapping("/load/{page}")
    public BaseResponse<GetPagingContactsRes> loadMyContactsByPaging(@PathVariable("page") int page, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);
        GetPagingContactsRes contactRes = contactService.getMyPagingContacts(page, JwtInfo.getMemberId(request));
        log.info("Load paging contact : {}", "memberId - " + memberId);

        return new BaseResponse<>(contactRes);
    }

    @ApiOperation(
            value = "설문 발송 대상자 불러오기 (Keyword)",
            notes = "페이징 처리된 자신의 설문 발송 대상자들을 불러옵니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2018, message = "존재하지 않는 연락처입니다."),
            @ApiResponse(code = 4004, message = "존재하지 않는 페이지입니다.")
    })
    @GetMapping("/find/{page}")
    public BaseResponse<GetPagingContactsRes> loadMyContactsByPaging(@RequestParam String keyword, @PathVariable("page") int page, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);
        GetPagingContactsRes contactRes = contactService.getMyPagingContactsByKeyword(page, keyword, JwtInfo.getMemberId(request));
        log.info("Load paging by keyword contact : {}", "memberId - " + memberId);

        return new BaseResponse<>(contactRes);
    }

    @ApiOperation(
            value = "설문 발송 대상자 삭제하기",
            notes = "이메일 주소를 통해 자신의 설문 발송 대상자를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2018, message = "존재하지 않는 연락처입니다.")
    })
    @PatchMapping("/delete")
    public BaseResponse<String> deleteContact(HttpServletRequest request, @RequestParam String email){
        contactService.deleteContact(email, JwtInfo.getMemberId(request));
        log.info("Delete contact: {}", "memberId - " + JwtInfo.getMemberId(request) + " | email - " + email);

        return new BaseResponse<>(CONTACT_DELETE_SUCCESS);
    }
}
