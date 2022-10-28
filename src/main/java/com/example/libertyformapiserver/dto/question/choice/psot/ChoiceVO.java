package com.example.libertyformapiserver.dto.question.choice.psot;

import com.example.libertyformapiserver.domain.Choice;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChoiceVO {
    @ApiModelProperty(
            example = "내용을 입력해주세요."
    )
    private String name;

    @ApiModelProperty(
            example = "1"
    )
    private int number;

    public Choice toEntity(){
        return Choice.builder()
                .name(name)
                .number(number)
                .build();
    }
}
