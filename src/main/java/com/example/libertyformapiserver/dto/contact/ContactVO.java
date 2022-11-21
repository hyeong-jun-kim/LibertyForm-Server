package com.example.libertyformapiserver.dto.contact;

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
public class ContactVO {
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

    public static ContactVO toDto(Contact contact){
        return ContactVO.builder()
                .email(contact.getEmail())
                .relationship(contact.getRelationship())
                .name(contact.getName())
                .isMember(contact.getMember() != null)
                .build();
    }

    static public List<ContactVO> toListDto(List<Contact> contacts){
        return contacts.stream().map(ContactVO::toDto).collect(Collectors.toList());
    }
}
