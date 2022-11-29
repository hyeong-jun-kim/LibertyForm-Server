package com.example.libertyformapiserver.dto.surveyManagement.get;

import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.SurveyManagement;
import com.example.libertyformapiserver.dto.surveyManagement.vo.SurveyManagementVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetSurveyManagementRes {
    List<SurveyManagementVO> surveyManagements;

    static public GetSurveyManagementRes toDto(List<SurveyManagement> surveyManagements){
        return builder()
                .surveyManagements(surveyManagements.stream().map(s -> SurveyManagementVO.toDto(s))
                        .collect(Collectors.toList()))
                .build();
    }
}
