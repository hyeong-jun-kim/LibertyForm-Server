package com.example.libertyformapiserver.dto.response.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponseReq {
    @ApiModelProperty(
            example = "3"
    )
    private long surveyId;

    private List<PostTextResponseReq> textResponse;

    private List<PostNumericResponseReq> numericResponse;

    private List<PostSingleChoiceResponseReq> singleChoiceResponse;

    private List<PostMultipleChoiceResponseReq> multipleChoiceResponse;
}
