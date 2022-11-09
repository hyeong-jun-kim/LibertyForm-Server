package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.email.post.PostSurveyEmailReq;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.service.SurveySendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.SEND_SURVEY_SUCCESS;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/send")
public class SurveySendController {
    private final SurveySendService surveySendService;

    @PostMapping("/email")
    public BaseResponse<String> sendSurveyEmail(@RequestBody PostSurveyEmailReq postSurveyEmailReq, HttpServletRequest request){
        surveySendService.sendSurveyEmail(postSurveyEmailReq, JwtInfo.getMemberId(request));

        List<String> receivers = PostSurveyEmailReq.toStringEmail(postSurveyEmailReq.getReceivers());
        log.info("Send survey-email {}", receivers.toString());

        return new BaseResponse<String>(SEND_SURVEY_SUCCESS);
    }
}
