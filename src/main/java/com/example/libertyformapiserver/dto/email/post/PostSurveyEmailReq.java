package com.example.libertyformapiserver.dto.email.post;

import com.example.libertyformapiserver.dto.email.vo.EmailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSurveyEmailReq {
    @ApiModelProperty(
            example = "1"
    )
    private long surveyId;

    private List<EmailVO> receivers;

    static public List<String> toStringEmail(List<EmailVO> emailVOList){
        return emailVOList.stream().map(e -> e.getEmail()).collect(Collectors.toList());
    }
}
