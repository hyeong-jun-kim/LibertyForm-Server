package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.vo.ChoiceVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class PostMultipleChoiceReq {
    @ApiModelProperty(
            example = "4"
    )
    private long questionId;

    private List<ChoiceVO> choices;

    public MultipleChoiceResponse toEntity(Response response, Question question){
        return MultipleChoiceResponse.builder()
                .response(response)
                .question(question)
                .build();
    }
}
