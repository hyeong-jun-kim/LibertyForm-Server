package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyReq;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyRes;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping("/create")
    @NoIntercept // TODO 추후에 제거해주기
    public BaseResponse<PostCreateSurveyRes> createSurvey(@RequestBody PostCreateSurveyReq surveyReqDto){
        return new BaseResponse<>(surveyService.createSurvey(surveyReqDto));
    }
}
