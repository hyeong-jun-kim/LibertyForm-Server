package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.survey.get.GetSurveyInfoRes;
import com.example.libertyformapiserver.dto.surveyManagement.get.GetSurveyManagementRes;
import com.example.libertyformapiserver.dto.surveyManagement.post.PostSurveyManagementReq;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.service.SurveyManagementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@RestController
@CrossOrigin(origins = "*")
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/manage")
public class SurveyManagementController {
    private final SurveyManagementService surveyManagementService;

    @ApiOperation(
            value = "설문 대상자들에게 설문 메일 보내기",
            notes = "연락처에 등록되어 있는 지정한 이메일 주소로 설문 대상자들에게 설문 메일을 보냅니다."
    )
    @ApiResponses({
            @ApiResponse(code = 2500, message = "해당 사용자들에게 설문이 발송이 정상적으로 수행되었습니다."),
            @ApiResponse(code = 2010, message = "존재하는 사용자가 아닙니다."),
            @ApiResponse(code = 2013, message = "존재하지 않는 설문입니다."),
            @ApiResponse(code = 2014, message = "해당 사용자의 설문이 아닙니다."),
            @ApiResponse(code = 2018, message = "연락처가 존재하지 않습니다."),
            @ApiResponse(code = 2501, message = "해당 사용자들에게 설문이 발송이 정상적으로 수행되었습니다.")
    })
    @PostMapping("/create")
    public BaseResponse<String> createSurveyManagement(@RequestBody PostSurveyManagementReq req, HttpServletRequest request){
        long memberId = JwtInfo.getMemberId(request);

        surveyManagementService.createSurveyManagement(req, JwtInfo.getMemberId(request));
        log.info("surveyManagementCreated: ", memberId);

        return new BaseResponse<>(SURVEY_MANAGEMENT_CREATED);
    }

    @ApiOperation(
            value = "피설문자 설문 읽음 처리",
            notes = "SurveyManagement에 등록되어있는 code로 설문지 읽음 처리를 진행할 수 있다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2020, message = "설문지 코드가 올바르지 않습니다.")
    })
    @NoIntercept
    @GetMapping("/read/{code}")
    public BaseResponse<GetSurveyInfoRes> readSurvey(@PathVariable String code){
        GetSurveyInfoRes surveyInfoRes = surveyManagementService.readSurvey(code);

        log.info("surveyManagementRead: ", code);
        return new BaseResponse<>(surveyInfoRes);
    }

    @ApiOperation(
            value = "설문 발송 대상자 모두 불러오기",
            notes = "설문 발송 대상자 모두 불러오기"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.")
    })
    @GetMapping("/load")
    public BaseResponse<GetSurveyManagementRes> getSurveyManagement(@RequestParam long surveyId, HttpServletRequest request){
        GetSurveyManagementRes getSurveyManagementRes = surveyManagementService.getSurveyManagements(surveyId, JwtInfo.getMemberId(request));

        log.info("surveyManagementLoad: ", JwtInfo.getMemberId(request));
        return new BaseResponse<>(getSurveyManagementRes);
    }

    @ApiOperation(
            value = "피설문자 설문 제출 완료 처리",
            notes = "SurveyManagement에 등록되어있는 code로 설문지 완료 처리를 진행할 수 있다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2020, message = "설문지 코드가 올바르지 않습니다."),
            @ApiResponse(code = 2503, message = "해당 설문지에 대해 설문 제출 응답처리가 되었습니다.")
    })
    @NoIntercept
    @GetMapping("/submit/{code}")
    public BaseResponse<String> submitSurvey(@PathVariable String code){
        surveyManagementService.submitSurvey(code);

        log.info("surveyManagementComplete: ", code);
        return new BaseResponse<>(SURVEY_MANAGEMENT_SUBMIT);
    }
}
