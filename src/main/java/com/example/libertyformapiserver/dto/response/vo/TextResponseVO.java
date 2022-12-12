package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.domain.TextResponse;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TextResponseVO extends CommonResponseVO {
    public TextResponseVO(long responseCnt, PostQuestionRes question){
        super(responseCnt, question);
    }

    private String wordCloudImgUrl;
    private ResponseVO responses;
    private EmotionVO emotions;

    public void setResponsesAndEmotions(List<TextResponse> textResponses){
        this.responses = ResponseVO.toEntity(textResponses.stream().map(t -> t.getValue()).collect(Collectors.toList()));
        this.emotions = EmotionVO.toEntity(textResponses.stream().map(t -> t.getEmotion().toString()).collect(Collectors.toList()));
    }

    public void setWordCloudImgUrl(String url){
        this.wordCloudImgUrl = url;
    }
}
