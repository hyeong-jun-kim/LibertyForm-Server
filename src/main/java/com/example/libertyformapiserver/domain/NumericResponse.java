package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.config.type.NumericType;
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
public class NumericResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Question question;

    @Enumerated(EnumType.STRING)
    private NumericType numericType;

    private int value;

    /* 편의 메서드 */
    public NumericResponse(Response response, Question question, NumericType numericType, int value) {
        this.response = response;
        this.question = question;
        this.numericType = numericType;
        this.value = value;
    }
}
