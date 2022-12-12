package com.example.libertyformapiserver.dto.response.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResponseVO {
    private List<String> responses;

    public static ResponseVO toEntity(List<String> responses){
        return ResponseVO.builder()
                .responses(responses)
                .build();
    }
}
