package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostSingleChoiceResponseReq {
    @ApiModelProperty(
            example = "3"
    )
    private int questionNumber;

    @ApiModelProperty(
            example = "1"
    )
    private int choiceNumber;

    public SingleChoiceResponse toEntity(Response response, Question question, Choice choice){
        return SingleChoiceResponse.builder()
                .response(response)
                .question(question)
                .choice(choice)
                .build();
    }
}
