package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.NumericResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class PostNumericResponseRes {
    private long questionId;

    private int value;

    private String type;

    public static PostNumericResponseRes toDto(NumericResponse numericResponse){
        return PostNumericResponseRes.builder()
                .questionId(numericResponse.getQuestion().getId())
                .value(numericResponse.getValue())
                .type(numericResponse.getNumericType().toString())
                .build();
    }

    public static List<PostNumericResponseRes> toListDto(List<NumericResponse> numericResponseList){
        return numericResponseList.stream().map(n -> toDto(n)).collect(Collectors.toList());
    }
}
