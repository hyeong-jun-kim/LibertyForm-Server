package com.example.libertyformapiserver.dto.survey.patch;

import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.Survey;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatchSurveyReq {
    @ApiModelProperty(
            example = "1"
    )
    private long surveyId;

    @ApiModelProperty(
            example = "설문지 제목"
    )
    @NotBlank(message = "설문지 제목을 입력해주세요.")
    @Size(min = 1, max = 30, message = "설문지 제목은 1 ~ 30자 이내만 입력 가능합니다.")
    private String name;

    @Size(max = 50, message = "설문지 설명은 30자 이내만 입력 가능합니다.")
    @ApiModelProperty(
            example = "설문지 설명"
    )
    private String description;

    @ApiModelProperty(
            example = "2022-10-30"
    )
    @NotBlank(message = "만료 일자를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expirationDate;
}
