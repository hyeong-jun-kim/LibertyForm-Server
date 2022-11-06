package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.config.status.EmailValidStatus;
import com.example.libertyformapiserver.config.type.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private EmailValidStatus email_valid_status;

    // 연관 관계 편의 메서드
    public Member(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
