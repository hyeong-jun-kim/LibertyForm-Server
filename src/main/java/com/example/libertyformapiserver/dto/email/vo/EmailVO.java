package com.example.libertyformapiserver.dto.email.vo;

import com.example.libertyformapiserver.jwt.NoIntercept;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailVO {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;
}
