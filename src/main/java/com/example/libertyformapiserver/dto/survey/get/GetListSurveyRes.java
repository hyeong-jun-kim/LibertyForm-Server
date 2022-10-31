package com.example.libertyformapiserver.dto.survey.get;

import com.example.libertyformapiserver.domain.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

// 설문지 모두 불러오는 DTO
@Getter
@Builder
@AllArgsConstructor
public class GetListSurveyRes {
    private List<GetSurveyRes> surveys;

    static public GetListSurveyRes listEntitytoDto(List<Survey> surveys){
        List<GetSurveyRes> surveyResList =
                surveys.stream().map(s -> GetSurveyRes.toDto(s)).collect(Collectors.toList());

        return GetListSurveyRes.builder()
                .surveys(surveyResList)
                .build();
    }
}
