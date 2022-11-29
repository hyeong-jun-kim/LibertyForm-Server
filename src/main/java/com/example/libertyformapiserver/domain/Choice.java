package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.dto.choice.patch.PatchChoiceReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Choice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private int number;

    private String name;

    // 편의 메서드
    public void update(PatchChoiceReq choice){
        this.number = choice.getNumber();
        this.name = choice.getName();
        this.changeStatusActive();
    }

    public void removeId(){
        this.id = 0;
    }
}
