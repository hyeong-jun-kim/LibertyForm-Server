package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.vo.ChoiceNumberVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostMultipleChoiceResponseReq {
    @ApiModelProperty(
            example = "4"
    )
    private int questionNumber;

    private List<ChoiceNumberVO> choices;

    public MultipleChoiceResponse toEntity(Response response, Question question){
        return MultipleChoiceResponse.builder()
                .response(response)
                .question(question)
                .build();
    }
}
