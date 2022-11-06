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
public class Choice_MultipleChoice_Response extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "multipleChoiceResponseId")
    private MultipleChoiceResponse multipleChoiceResponse;

    @OneToOne
    @JoinColumn(name = "choiceId")
    private Choice choice;

    /* 편의 메서드 */
    public Choice_MultipleChoice_Response(MultipleChoiceResponse multipleChoiceResponse, Choice choice){
        this.multipleChoiceResponse = multipleChoiceResponse;
        this.choice = choice;
    }
}
