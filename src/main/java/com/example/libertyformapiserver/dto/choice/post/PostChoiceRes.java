package com.example.libertyformapiserver.dto.choice.post;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostChoiceRes {
    @ApiModelProperty(
            example = "3"
    )
    private long questionId;

    @ApiModelProperty(
            example = "객관식 보기 문항입니다."
    )
    private String name;

    @ApiModelProperty(
            example = "1"
    )
    private int number;

    static public PostChoiceRes toDto(Choice choice){
        return PostChoiceRes.builder()
                .questionId(choice.getQuestion().getId())
                .name(choice.getName())
                .number(choice.getNumber())
                .build();
    }
}
