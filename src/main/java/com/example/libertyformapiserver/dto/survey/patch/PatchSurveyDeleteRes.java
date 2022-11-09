package com.example.libertyformapiserver.dto.survey.patch;

import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Survey;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PatchSurveyDeleteRes {
    @ApiModelProperty(
            example = "5"
    )
    private long surveyId;

    @ApiModelProperty(
            example = "INACTIVE"
    )
    private BaseStatus baseStatus;

    static public PatchSurveyDeleteRes toDto(Survey survey){
        return PatchSurveyDeleteRes.builder()
                .surveyId(survey.getId())
                .baseStatus(survey.getStatus())
                .build();
    }
}
