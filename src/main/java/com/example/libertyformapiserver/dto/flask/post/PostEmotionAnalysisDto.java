package com.example.libertyformapiserver.dto.flask.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEmotionAnalysisDto {
    private HashMap<Long, Integer> emotionLists;
}
