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
            example = "vf8dA1f23gH2"
    )
    private String code;

    @ApiModelProperty(
            example = "화장품 설문"
    )
    private String name;

    @ApiModelProperty(
            example = "https://objectstorage.kr-central-1.kakaoi.io/v1/586d691a32c5421b859e89fd7a7f8dcd/libertyform/img/survey/thumbnail/c0af32af-bfee-4e48-8260-27ac1cb5fe87-test.PNG"
    )
    private String thumbnailImgUrl;

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
                .code(survey.getCode())
                .name(survey.getName())
                .description(survey.getDescription())
                .thumbnailImgUrl(survey.getThumbnailImg())
                .expirationDate(survey.getExpirationDate())
                .createdAt(survey.getCreatedAt())
                .build();
    }
}
