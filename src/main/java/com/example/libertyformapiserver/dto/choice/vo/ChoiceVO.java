package com.example.libertyformapiserver.dto.choice.vo;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.dto.response.vo.ChoiceResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChoiceVO {
    @ApiModelProperty(
            example = "갤럭시"
    )
    private String name;

    @ApiModelProperty(
            example = "3"
    )
    private int choiceNumber;

    static public ChoiceVO toDto(Choice choice){
        return ChoiceVO.builder()
                .name(choice.getName())
                .choiceNumber(choice.getNumber())
                .build();
    }
}
