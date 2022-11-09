package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.TextResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Builder
public class PostTextResponseRes {
    private long questionId;

    private String value;

    private String type;

    static public PostTextResponseRes toDto(TextResponse textResponse){
        return PostTextResponseRes.builder()
                .questionId(textResponse.getQuestion().getId())
                .value(textResponse.getValue())
                .type(textResponse.getTextType().toString())
                .build();
    }

    static public List<PostTextResponseRes> toListDto(List<TextResponse> textResponseList){
        return textResponseList.stream().map(t -> toDto(t)).collect(Collectors.toList());
    }
}
