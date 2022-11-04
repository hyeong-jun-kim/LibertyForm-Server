package com.example.libertyformapiserver.dto.survey.patch;

import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.survey.post.PostSurveyRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
