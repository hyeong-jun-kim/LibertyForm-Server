package com.example.libertyformapiserver.dto.survey.patch;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchSurveyDeleteReq {
    @ApiModelProperty(
            example = "1"
    )
    private long surveyId;
}
