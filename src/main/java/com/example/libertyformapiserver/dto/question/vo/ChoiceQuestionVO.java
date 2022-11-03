package com.example.libertyformapiserver.dto.question.vo;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
import com.example.libertyformapiserver.jwt.NoIntercept;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ChoiceQuestionVO {
    private PostQuestionReq question;

    private List<PostChoiceReq> choices;

    static public ChoiceQuestionVO toVO(Question question, List<Choice> choices) {
        PostQuestionReq questionDto = PostQuestionReq.toDto(question);
        List<PostChoiceReq> choicesDto = choices.stream()
                .map(c -> PostChoiceReq.toDto(c)).collect(Collectors.toList());

        return ChoiceQuestionVO.builder().question(questionDto).choices(choicesDto).build();
    }
}
