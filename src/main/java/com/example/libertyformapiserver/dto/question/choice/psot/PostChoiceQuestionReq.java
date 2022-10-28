package com.example.libertyformapiserver.dto.question.choice.psot;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.QuestionType;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.question.post.PostQuestionsReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostChoiceQuestionReq {
    @ApiModelProperty(
            example = "1"
    )
    private Integer questionTypeId;

    @ApiModelProperty(
            example = "당신이 좋아하는 과일은 어떤걸까요?"
    )
    private String name;

    @ApiModelProperty(
            example = "예를들어서 사과, 오렌지, 포도가 있습니다."
    )
    private String description;

    @ApiModelProperty(
            example = "1"
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

    private List<ChoiceVO> choices;

    public Question toQuestionEntity(Survey survey, QuestionType questionType){
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

    public List<Choice> toListChoiceEntity(){
        return choices.stream()
                .map(ChoiceVO::toEntity)
                .collect(Collectors.toList());
    }
}
