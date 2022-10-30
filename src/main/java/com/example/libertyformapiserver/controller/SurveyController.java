package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.survey.create.post.PostCreateSurveyReq;
import com.example.libertyformapiserver.dto.survey.create.post.PostCreateSurveyRes;
import com.example.libertyformapiserver.dto.survey.get.GetListSurveyRes;
import com.example.libertyformapiserver.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @ApiOperation(
            value = "설문지 생성",
            notes = "survey - 설문 내용, questions - 주관식, 감정, 선형대수 문항," +
                    " choiceQuestions - 객관식 문항"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2011, message = "질문 유형 번호를 다시한번 확인해주시길 바랍니다."),
            @ApiResponse(code = 2012, message = "질문 번호 순서가 올바르지 않습니다. 질문 번호를 다시한번 확인해주시기 바랍니다."),
            @ApiResponse(code = 4001, message = "존재하지 않는 질문 유형입니다.")}
    )
    @PostMapping("/create")
    public BaseResponse<PostCreateSurveyRes> createSurvey(@RequestBody PostCreateSurveyReq surveyReqDto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse<>(surveyService.createSurvey(surveyReqDto, jwtInfo.getMemberId()));
    }

    @ApiOperation(
            value = "설문지 모두 불러오기",
            notes = "해당 유저에 대한 설문을 모두 불러옵니다. (대시보드 용)"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.")
    })
    @GetMapping
    public BaseResponse<GetListSurveyRes> getAllUserSurvey(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return new BaseResponse<>(surveyService.getAllUserSurvey(jwtInfo.getMemberId()));
    }
}
