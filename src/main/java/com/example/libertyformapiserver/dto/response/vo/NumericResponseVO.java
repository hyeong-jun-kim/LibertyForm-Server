package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
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
public class NumericResponseVO extends CommonResponseVO {
    public NumericResponseVO(long responseCnt, PostQuestionRes question){
        super(responseCnt, question);
    }
    private List<Integer> responses;

    public void setResponses(List<NumericResponse> numericResponses){
        this.responses = numericResponses.stream().map(n -> n.getValue()).collect(Collectors.toList());
    }
}
