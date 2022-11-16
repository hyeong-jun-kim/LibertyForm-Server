package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.surveyManagement.post.PostSurveyManagementReq;
import com.example.libertyformapiserver.service.SurveyManagementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.SURVEY_MANAGEMENT_CREATED;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/manage")
public class SurveyManagementController {
    private final SurveyManagementService surveyManagementService;

    @ApiOperation(
            value = "설문 대상자들에게 설문 메일 보내기",
            notes = "지정한 이메일 주소로 설문 대상자들에게 설문 메일을 보냅니다."
    )
    @ApiResponses({
            @ApiResponse(code = 2500, message = "해당 사용자들에게 설문이 발송이 정상적으로 수행되었습니다."),
            @ApiResponse(code = 2010, message = "존재하는 사용자가 아닙니다."),
            @ApiResponse(code = 2013, message = "존재하지 않는 설문입니다."),
            @ApiResponse(code = 2014, message = "해당 사용자의 설문이 아닙니다."),
            @ApiResponse(code = 2018, message = "연락처가 존재하지 않습니다.")
    })
    @PostMapping("/create")
    public BaseResponse<String> createSurveyManagement(@RequestBody PostSurveyManagementReq req, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);

        surveyManagementService.createSurveyManagement(req, JwtInfo.getMemberId(request));
        log.info("surveyManagementCreated: ", memberId);

        return new BaseResponse<>(SURVEY_MANAGEMENT_CREATED);
    }


}
