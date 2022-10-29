package com.example.libertyformapiserver.dto.survey.create;

import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceRes;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import com.example.libertyformapiserver.dto.survey.post.PostSurveyRes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostCreateSurveyRes {
    private PostSurveyRes survey;

    private List<PostQuestionRes> questions;

    private List<PostChoiceRes> choices;

    public PostCreateSurveyRes(Survey survey){
        this.survey = PostSurveyRes.toDto(survey);
    }

    public void addListQuestion(List<PostQuestionRes> questionList){
        if(questions == null)
            questions = new ArrayList<>();

        questionList.stream()
                .forEach(q -> questions.add(q));
    }

    public void addListChoice(List<PostChoiceRes> choiceList){
        if(choices == null)
            choices = new ArrayList<>();

        choiceList.stream()
                .forEach(q -> choices.add(q));
    }
}
