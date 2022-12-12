package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.config.type.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EmotionVO {
    private List<String> emotions;

    public static EmotionVO toEntity(List<String> emotions){
        return EmotionVO.builder()
                .emotions(emotions)
                .build();
    }
}
