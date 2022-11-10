package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 연락처에 등록된 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String email;

    private String name;

    private String relationship;

    // 편의 메서드
    public Contact(String email, String name, String relationship){
        this.email = email;
        this.name = name;
        this.relationship = relationship;
    }

    public void changeMember(Member member){
        this.member = member;
    }
}
