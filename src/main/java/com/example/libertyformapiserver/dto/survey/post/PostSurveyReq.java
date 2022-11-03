package com.example.libertyformapiserver.dto.survey.post;

import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.Survey;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostSurveyReq {
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

    public Survey toEntity(Member member){
        return Survey.builder()
                .member(member)
                .name(name)
                .description(description)
                .expirationDate(expirationDate)
                .build();
    }
}
