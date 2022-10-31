package com.example.libertyformapiserver.dto.choice.post;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostChoiceReq {
    @ApiModelProperty(
            example = "내용을 입력해주세요."
    )
    private String name;

    @ApiModelProperty(
            example = "1"
    )
    private int number;

    public Choice toEntity(Question question){
        return Choice.builder()
                .question(question)
                .name(name)
                .number(number)
                .build();
    }

    static public PostChoiceReq toDto(Choice choice){
        return PostChoiceReq.builder()
                .name(choice.getName())
                .number(choice.getNumber())
                .build();
    }
}
