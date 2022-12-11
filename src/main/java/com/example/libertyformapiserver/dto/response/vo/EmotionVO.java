package com.example.libertyformapiserver.dto.response.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmotionVO {
    private List<String> emotions;
}
