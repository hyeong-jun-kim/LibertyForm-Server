package com.example.libertyformapiserver.dto.question.patch;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.dto.choice.patch.PatchChoiceReq;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
import lombok.*;

import javax.sound.midi.Patch;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PatchChoiceQuestionReq {
    private PatchQuestionReq question;

    private List<PatchChoiceReq> choices;

    public long getQuestionId(){
        return question.getQuestionId();
    }
}
