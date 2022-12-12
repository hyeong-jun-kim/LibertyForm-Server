package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.domain.TextResponse;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "https://objectstorage.kr-central-1.kakaoi.io/v1/586d691a32c5421b859e89fd7a7f8dcd/libertyform/img/survey/wordcloud/bc3720c3-605e-4a6a-95ba-ebd61e1a43e5-Survey_Wordcloud_long.png")
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
