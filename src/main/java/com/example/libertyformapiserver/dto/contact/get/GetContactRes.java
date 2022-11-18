package com.example.libertyformapiserver.dto.contact.get;

import com.example.libertyformapiserver.domain.Contact;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetContactRes {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            example = "친구"
    )
    private String relationship;

    @ApiModelProperty(
            example = "김형준"
    )
    private String name;

    @ApiModelProperty(
            example = "true"
    )
    private boolean isMember;

    public static GetContactRes toDto(Contact contact){
        return GetContactRes.builder()
                .email(contact.getEmail())
                .relationship(contact.getRelationship())
                .name(contact.getName())
                .isMember(contact.getMember() != null)
                .build();
    }

    static public List<GetContactRes> toListDto(List<Contact> contacts){
        return contacts.stream().map(GetContactRes::toDto).collect(Collectors.toList());
    }
}
