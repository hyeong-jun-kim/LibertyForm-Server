package com.example.libertyformapiserver.dto.surveyAnalysis.get;

import com.example.libertyformapiserver.dto.response.vo.ChoiceResponseVO;
import com.example.libertyformapiserver.dto.response.vo.NumericResponseVO;
import com.example.libertyformapiserver.dto.response.vo.TextResponseVO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GetSurveyAnalysisRes {
    private String title;

    private long responseCnt;

    private LocalDateTime createdDate;

    private LocalDate expiredDate;

    private List<TextResponseVO> textResponses = new ArrayList<>();

    private List<NumericResponseVO> numericResponses = new ArrayList<>();

    private List<ChoiceResponseVO> choiceResponses = new ArrayList<>();

    // 편의 메서드
    public void addTextResponse(TextResponseVO textResponse){
        this.textResponses.add(textResponse);
    }

    public void addNumericResponse(NumericResponseVO numericResponse){
        this.numericResponses.add(numericResponse);
    }

    public void addChoiceResponse(ChoiceResponseVO choiceResponse){
        this.choiceResponses.add(choiceResponse);
    }

}
