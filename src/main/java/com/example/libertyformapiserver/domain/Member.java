package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.config.type.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private MemberType member_type;

    @OneToMany(mappedBy = "member")
    private List<MemberContact> memberContacts = new ArrayList<>();

    // 연관 관계 편의 메서드
    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // 설문 발송 대상자 추가
    public void addContact(MemberContact memberContact){
        memberContact.changeMember(this);
        memberContacts.add(memberContact);
    }
}
