package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class QuestionType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean hasChoices;

    // 편의 메서드
    public void changeQuestionType(long id){
        this.id = id;
    }
}
