package com.example.libertyformapiserver.dto.response.vo;

import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResponseVO {
    @ApiModelProperty(
            example = "142"
    )
    private long responseCnt; // 응답자 수

    private PostQuestionRes question;
}
