package com.example.libertyformapiserver.dto.login.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostLoginReq {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @ApiModelProperty(
            example = "1q2w3e4r!"
    )
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;
}