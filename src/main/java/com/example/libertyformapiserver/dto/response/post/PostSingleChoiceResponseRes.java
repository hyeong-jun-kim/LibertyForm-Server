package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.SingleChoiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Builder
public class PostSingleChoiceResponseRes {
    private long questionId;

    private long choiceId;

    static public PostSingleChoiceResponseRes toDto(SingleChoiceResponse singleChoiceResponse){
        return PostSingleChoiceResponseRes.builder()
                .questionId(singleChoiceResponse.getQuestion().getId())
                .choiceId(singleChoiceResponse.getQuestion().getId())
                .build();
    }

    public static List<PostSingleChoiceResponseRes> toListDto(List<SingleChoiceResponse> singleChoiceResponseList){
        return singleChoiceResponseList.stream().map(s -> toDto(s)).collect(Collectors.toList());
    }
}
