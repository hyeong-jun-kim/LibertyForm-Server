package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.SingleChoiceResponse;
import com.example.libertyformapiserver.domain.TextResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponseRes {
    @ApiModelProperty(
            example = "3"
    )
    private long responseId;

    private List<PostTextResponseRes> textResponse;

    private List<PostNumericResponseRes> numericResponse;

    private List<PostSingleChoiceResponseRes> singleChoiceResponse;

    private List<PostMultipleChoiceResponseRes> multipleChoiceResponse;

}
