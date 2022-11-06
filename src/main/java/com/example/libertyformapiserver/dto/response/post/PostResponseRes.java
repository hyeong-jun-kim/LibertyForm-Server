package com.example.libertyformapiserver.dto.response.post;

import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.SingleChoiceResponse;
import com.example.libertyformapiserver.domain.TextResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponseRes {
    private List<PostTextResponseRes> textResponse;

    private List<PostNumericResponseRes> numericResponse;

    private List<PostSingleChoiceResponseRes> singleChoiceResponse;

    private List<PostMultipleChoiceResponseRes> multipleChoiceResponse;
}
