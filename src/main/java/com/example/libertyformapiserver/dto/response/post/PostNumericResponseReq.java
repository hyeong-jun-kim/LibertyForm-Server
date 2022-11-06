package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostNumericResponseReq {
    @ApiModelProperty(
            example = "1"
    )
    private long questionId;

    @ApiModelProperty(
            example = "4"
    )
    private int value;

    @ApiModelProperty(
            example = "EMOTION_BAR"
    )
    private NumericType type;

    public NumericResponse toEntity(Response response, Question question){
        return NumericResponse.builder()
                .response(response)
                .question(question)
                .value(value)
                .numericType(type)
                .build();
    }
}
