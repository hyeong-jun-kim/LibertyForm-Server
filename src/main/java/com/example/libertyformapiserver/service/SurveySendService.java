package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.email.post.PostSurveyEmailReq;
import com.example.libertyformapiserver.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_EXIST_SURVEY;
import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_MATCH_SURVEY;

@RequiredArgsConstructor
@Service
public class SurveySendService {
    private final EmailService emailService;

    private final SurveyRepository surveyRepository;

    public void sendSurveyEmail(PostSurveyEmailReq surveyEmailDto, long memberId){
        long surveyId = surveyEmailDto.getSurveyId();

        Survey survey = surveyRepository.findById(surveyId).orElseThrow(
                () -> new BaseException(NOT_EXIST_SURVEY));

        if(memberId != survey.getMember().getId())
            throw new BaseException(NOT_MATCH_SURVEY);

        List<String> receivers = PostSurveyEmailReq.toStringEmail(surveyEmailDto.getReceivers());

        emailService.sendSurveyEmail(survey, receivers);
    }
}
