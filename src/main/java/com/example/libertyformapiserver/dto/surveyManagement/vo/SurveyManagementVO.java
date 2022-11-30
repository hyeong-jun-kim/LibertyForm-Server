package com.example.libertyformapiserver.dto.surveyManagement.vo;

import com.example.libertyformapiserver.config.status.ResponseStatus;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.domain.SurveyManagement;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SurveyManagementVO {
    @ApiModelProperty(
            example = "설문지 제목"
    )
    private String surveyName;

    @ApiModelProperty(
            example = "연락처 이름"
    )
    private String contactName;

    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            example = "친구"
    )
    private String relationship;

    @ApiModelProperty(
            example = "ABCe12das2q"
    )
    private String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @ApiModelProperty(
            example = "2022-12-31"
    )
    private LocalDate expiredDate;

    @ApiModelProperty(
            example = "PENDING"
    )
    private ResponseStatus responseStatus;

    static public SurveyManagementVO toDto(SurveyManagement surveyManagement){
        Survey survey = surveyManagement.getSurvey();
        Contact contact =surveyManagement.getContact();

        return SurveyManagementVO.builder()
                .surveyName(survey.getName())
                .expiredDate(survey.getExpirationDate())
                .contactName(contact.getName())
                .email(contact.getEmail())
                .relationship(contact.getRelationship())
                .code(surveyManagement.getCode())
                .responseStatus(surveyManagement.getResponseStatus())
                .build();
    }

}
