package com.example.libertyformapiserver.dto.survey.post;

import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            example = "2022-10-30 11:00:00"
    )
    private LocalDateTime expirationDate;

    public Survey toEntity(){
        return Survey.builder()
                .name(name)
                .description(description)
                .expirationDate(expirationDate)
                .build();
    }
}
