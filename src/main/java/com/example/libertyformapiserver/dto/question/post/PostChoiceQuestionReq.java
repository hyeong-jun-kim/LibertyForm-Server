package com.example.libertyformapiserver.dto.question.post;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PostChoiceQuestionReq {
    private PostQuestionReq question;

    private List<PostChoiceReq> choices;

    static public PostChoiceQuestionReq toVO(Question question, List<Choice> choices) {
        PostQuestionReq questionDto = PostQuestionReq.toDto(question);
        List<PostChoiceReq> choicesDto = choices.stream()
                .map(c -> PostChoiceReq.toDto(c)).collect(Collectors.toList());

        return PostChoiceQuestionReq.builder().question(questionDto).choices(choicesDto).build();
    }
}
