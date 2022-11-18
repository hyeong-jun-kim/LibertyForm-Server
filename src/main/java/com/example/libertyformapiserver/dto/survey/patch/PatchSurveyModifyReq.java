package com.example.libertyformapiserver.dto.survey.patch;

import com.example.libertyformapiserver.dto.question.patch.PatchChoiceQuestionReq;
import com.example.libertyformapiserver.dto.question.patch.PatchQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PatchSurveyModifyReq {
    private PatchSurveyReq survey;

    private List<PatchQuestionReq> questions;

    private List<PatchChoiceQuestionReq> choiceQuestions;
}
