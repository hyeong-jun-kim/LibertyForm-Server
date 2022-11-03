package com.example.libertyformapiserver.dto.member.post;

import com.example.libertyformapiserver.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRegisterRes {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            example = "김형준"
    )
    private String name;

    static public PostRegisterRes toDto(Member member){
        return PostRegisterRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}