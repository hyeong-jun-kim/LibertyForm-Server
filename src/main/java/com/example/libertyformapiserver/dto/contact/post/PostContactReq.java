package com.example.libertyformapiserver.dto.contact.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostContactReq {
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",
            message = "이메일 양식에 맞게 입력해 주시기 바랍니다.")
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @NotBlank
    @ApiModelProperty(
            example = "김형준"
    )
    private String name;

    @NotBlank
    @ApiModelProperty(
            example = "친구"
    )
    private String relationship;
}
