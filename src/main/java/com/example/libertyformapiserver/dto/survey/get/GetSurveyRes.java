package com.example.libertyformapiserver.dto.survey.get;

import com.example.libertyformapiserver.domain.Survey;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetSurveyRes {
    @ApiModelProperty(
            example = "1"
    )
    private long surveyId;

    @ApiModelProperty(
            example = "화장품 설문"
    )
    private String name;

    @ApiModelProperty(
            example = "화장품 설문입니다. 최선을 다해서 임해주시기 바랍니다."
    )
    private String description;

    @ApiModelProperty(
            example = "2022-10-30"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;


    @ApiModelProperty(
            example = "2022-10-28 10:00:00"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    static public GetSurveyRes toDto(Survey survey){
        return GetSurveyRes.builder()
                .surveyId(survey.getId())
                .name(survey.getName())
                .description(survey.getDescription())
                .expirationDate(survey.getExpirationDate())
                .createdAt(survey.getCreatedAt())
                .build();
    }
}
