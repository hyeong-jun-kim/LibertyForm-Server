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
@Getter
@Entity
public class MultipleChoice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multipleChoiceResponseId")
    private MultipleChoiceResponse multipleChoiceResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choiceId")
    private Choice choice;

    /* 편의 메서드 */
    public MultipleChoice(MultipleChoiceResponse multipleChoiceResponse, Choice choice){
        this.multipleChoiceResponse = multipleChoiceResponse;
        this.choice = choice;
    }
}
