package com.example.libertyformapiserver.dto.login.post;

import com.example.libertyformapiserver.config.status.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostLoginRes {
    private String jwt;
    private String email;
    private String name;
    private MemberType memberType;
}
