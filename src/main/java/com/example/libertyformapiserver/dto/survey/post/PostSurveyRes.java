package com.example.libertyformapiserver.dto.survey.post;

import com.example.libertyformapiserver.domain.Survey;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PostSurveyRes {
    @ApiModelProperty(
            example = "5"
    )
    private long memberId;

    @ApiModelProperty(
            example = "설문지 제목"
    )
    private String name;

    @ApiModelProperty(
            example = "설문지 설명"
    )
    private String description;

    @ApiModelProperty(
            example = "https://objectstorage.kr-central-1.kakaoi.io/v1/586d691a32c5421b859e89fd7a7f8dcd/libertyform/img/survey/thumbnail/765003d9-1137-4085-bfec-f861d5ee7c4e-스크린샷_20221023_042337.png"
    )
    private String thumbnailImgUrl;

    @ApiModelProperty(
            example = "2022-10-30"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    static public PostSurveyRes toDto(Survey survey){
        return PostSurveyRes.builder()
                .memberId(survey.getMember().getId())
                .name(survey.getName())
                .description(survey.getDescription())
                .thumbnailImgUrl(survey.getThumbnailImg())
                .expirationDate(survey.getExpirationDate())
                .build();
    }
}
