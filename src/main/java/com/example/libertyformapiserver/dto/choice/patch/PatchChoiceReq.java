package com.example.libertyformapiserver.dto.choice.patch;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PatchChoiceReq {
    @NotBlank(message = "객관식 문항 내용을 입력해주세요.")
    @Size(min = 1, max = 20, message = "1 ~ 20자 이내로 입력해주세요.")
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

    static public PatchChoiceReq toDto(Choice choice){
        return PatchChoiceReq.builder()
                .name(choice.getName())
                .number(choice.getNumber())
                .build();
    }
}
