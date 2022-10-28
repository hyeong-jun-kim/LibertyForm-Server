package com.example.libertyformapiserver.dto.survey.generate;

import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.question.choice.psot.PostChoiceQuestionReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionsReq;
import com.example.libertyformapiserver.dto.survey.post.PostSurveyReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostGenerateSurveyReq {
    private PostSurveyReq postSurveyReq;

    private List<PostQuestionReq> questions;

    private List<PostChoiceQuestionReq> choiceQuestions;
}
