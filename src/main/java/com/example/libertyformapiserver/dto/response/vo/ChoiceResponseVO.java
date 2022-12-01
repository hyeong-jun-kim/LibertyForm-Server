package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.MultipleChoice;
import com.example.libertyformapiserver.domain.MultipleChoiceResponse;
import com.example.libertyformapiserver.dto.choice.vo.ChoiceVO;
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
public class ChoiceResponseVO extends CommonResponseVO {
    public ChoiceResponseVO(long responseCnt, PostQuestionRes question){
        super(responseCnt, question);
    }
    private List<ChoiceVO> choices;

    private List<Integer> responses;

    public void setChoices(List<Choice> choices){
        this.choices = choices.stream().map(c -> ChoiceVO.toDto(c)).collect(Collectors.toList());
    }

    public void setResponses(List<Integer> responses){
        this.responses = responses;
    }
}
