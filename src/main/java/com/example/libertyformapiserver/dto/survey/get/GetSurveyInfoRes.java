package com.example.libertyformapiserver.dto.survey.get;

import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import com.example.libertyformapiserver.dto.question.vo.ChoiceQuestionVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetSurveyInfoRes {
    private GetSurveyRes survey;

    private List<PostQuestionRes> questions;

    private List<ChoiceQuestionVO> choiceQuestions;

    public static GetSurveyInfoRes toDto(Survey survey, List<Question> questionList){
        GetSurveyRes surveyDto = GetSurveyRes.toDto(survey);

        List<PostQuestionRes> questionResList = questionList.stream()
                .map(q -> PostQuestionRes.toDto(q)).collect(Collectors.toList());

        return GetSurveyInfoRes.builder()
                .survey(surveyDto)
                .questions(questionResList)
                .build();
    }
}
