package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.surveyAnalysis.get.GetSurveyAnalysisRes;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.service.SurveyAnalysisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class SurveyAnalysisController {
    private final SurveyAnalysisService surveyAnalysisService;

    // 설문분석 결과 불러오기
    @ApiOperation(
            value = "설문지 응답 불러오기",
            notes = "surveyId로 설문지의 응답을 모두 불러옵니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2013, message = "존재하지 않는 설문입니다."),
            @ApiResponse(code = 2014, message = "해당 사용자의 설문이 아닙니다.")
    })
    @GetMapping({"/load/{id}"})
    public BaseResponse<GetSurveyAnalysisRes> getSurveyAnalysis(@PathVariable("id") Long surveyId, HttpServletRequest request){
        GetSurveyAnalysisRes surveyAnalysisRes = surveyAnalysisService.getSurveyAnalysis(surveyId, JwtInfo.getMemberId(request));

        log.info("Survey-Analysis: ", surveyId);
        return new BaseResponse<>(surveyAnalysisRes);
    }
}
