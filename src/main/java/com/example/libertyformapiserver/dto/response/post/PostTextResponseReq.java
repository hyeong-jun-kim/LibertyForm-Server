package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.config.type.TextType;
import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Response;
import com.example.libertyformapiserver.domain.TextResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostTextResponseReq {
    @ApiModelProperty(
            example = "2"
    )
    private long questionId;

    @ApiModelProperty(
            example = "저는 사과가 좋아요 왜냐하면 맛있기 때문이죠."
    )
    private String value;

    @ApiModelProperty(
            example = "LONG_TEXT"
    )
    private TextType type;

    public TextResponse toEntity(Response response, Question question){
        return TextResponse.builder()
                .response(response)
                .question(question)
                .value(value)
                .textType(type)
                .build();
    }
}
