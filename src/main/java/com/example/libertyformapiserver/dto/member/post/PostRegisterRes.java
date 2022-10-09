package com.example.libertyformapiserver.dto.member.post;

import com.example.libertyformapiserver.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRegisterRes {
    private String email;
    private String name;

    static public PostRegisterRes toDto(Member member){
        return PostRegisterRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
