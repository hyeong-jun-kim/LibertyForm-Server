package com.example.libertyformapiserver.dto.question.post;

import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.QuestionType;
import com.example.libertyformapiserver.domain.Survey;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostQuestionReq {
    @ApiModelProperty(
            example = "3"
    )
    private long questionTypeId;

    @ApiModelProperty(
            example = "당신이 좋아하는 과일은 어떤걸까요?"
    )
    private String name;

    @ApiModelProperty(
            example = "예를들어서 사과, 오렌지, 포도가 있습니다."
    )
    private String description;

    @ApiModelProperty(
            example = "2"
    )
    private Integer number;

    @ApiModelProperty(
            example = "https://libertyform.shop/s3/topas.jpg"
    )
    private String backgroundImgUrl;

    @ApiModelProperty(
            example = "https://libertyform.shop/s3/neo.jpg"
    )
    private String questionImgUrl;

    @ApiModelProperty(
            example = "false"
    )
    private boolean answerRequired;

    public Question toEntity(Survey survey, QuestionType questionType){
        return Question.builder()
                .survey(survey)
                .questionType(questionType)
                .name(name)
                .description(description)
                .number(number)
                .backgroundImgUrl(backgroundImgUrl)
                .questionImgUrl(questionImgUrl)
                .answerRequired(answerRequired)
                .build();
    }

    public static PostQuestionReq toDto(Question question){
        return PostQuestionReq.builder()
                .questionTypeId(question.getQuestionType().getId())
                .name(question.getName())
                .description(question.getDescription())
                .number(question.getNumber())
                .backgroundImgUrl(question.getBackgroundImgUrl())
                .questionImgUrl(question.getQuestionImgUrl())
                .answerRequired(question.isAnswerRequired())
                .build();
    }
}
