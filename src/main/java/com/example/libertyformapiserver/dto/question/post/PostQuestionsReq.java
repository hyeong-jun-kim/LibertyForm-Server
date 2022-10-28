package com.example.libertyformapiserver.dto.question.post;

import com.example.libertyformapiserver.domain.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostQuestionsReq {
    private List<PostQuestionReq> questions;
}
