package com.example.libertyformapiserver.dto.surveyManagement.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSurveyManagementReq {
    @ApiModelProperty(
            example = "3"
    )
    private long surveyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @ApiModelProperty(
            example = "2022-11-15"
    )
    private LocalDate expiredDate;

    private List<String> emails;
}
