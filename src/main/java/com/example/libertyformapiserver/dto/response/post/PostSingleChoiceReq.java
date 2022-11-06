package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PostSingleChoiceReq {
    @ApiModelProperty(
            example = "3"
    )
    private long questionId;

    @ApiModelProperty(
            example = "1"
    )
    private int choiceId;

    public SingleChoiceResponse toEntity(Response response, Question question, Choice choice){
        return SingleChoiceResponse.builder()
                .response(response)
                .question(question)
                .choice(choice)
                .build();
    }
}
