package com.example.libertyformapiserver.dto.question.post;

import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.QuestionType;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostChoiceQuestionReq {
    private PostQuestionReq question;

    private List<PostChoiceReq> choices;

    public Question toQuestionEntity(Survey survey, QuestionType questionType){
        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .name(question.getName())
                .description(question.getDescription())
                .number(question.getNumber())
                .backgroundImgUrl(question.getBackgroundImgUrl())
                .questionImgUrl(question.getQuestionImgUrl())
                .answerRequired(question.isAnswerRequired())
                .build();
    }
}
