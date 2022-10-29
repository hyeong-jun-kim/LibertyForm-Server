package com.example.libertyformapiserver.dto.survey.post;

import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.Survey;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
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
            example = "2022-10-30"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;

    static public PostSurveyRes toDto(Survey survey){
        return PostSurveyRes.builder()
                .memberId(survey.getMember().getId())
                .name(survey.getName())
                .description(survey.getDescription())
                .expirationDate(survey.getExpirationDate())
                .build();
    }
}
