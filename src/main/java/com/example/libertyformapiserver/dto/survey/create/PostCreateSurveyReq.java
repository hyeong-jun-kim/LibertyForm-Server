package com.example.libertyformapiserver.dto.survey.create;

import com.example.libertyformapiserver.dto.question.post.PostChoiceQuestionReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
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
public class PostCreateSurveyReq {
    private PostSurveyReq survey;

    private List<PostQuestionReq> questions;

    private List<PostChoiceQuestionReq> choiceQuestions;
}
