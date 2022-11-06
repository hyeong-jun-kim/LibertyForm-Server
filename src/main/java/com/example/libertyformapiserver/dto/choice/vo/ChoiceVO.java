package com.example.libertyformapiserver.dto.choice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChoiceVO {
    @ApiModelProperty(
            example = "3"
    )
    private int choiceId;
}
