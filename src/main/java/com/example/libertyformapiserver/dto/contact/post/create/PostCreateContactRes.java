package com.example.libertyformapiserver.dto.contact.post.create;

import com.example.libertyformapiserver.domain.Contact;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostCreateContactRes {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            example = "김형준"
    )
    private String name;

    @ApiModelProperty(
            example = "친구"
    )
    private String relationship;

    @ApiModelProperty(
            example = "true"
    )
    private boolean isMember;

    static public PostCreateContactRes toDto(Contact contact){
        return PostCreateContactRes.builder()
                .email(contact.getEmail())
                .name(contact.getName())
                .relationship(contact.getRelationship())
                .isMember(contact.getMember() != null)
                .build();
    }
}
