package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.config.type.TextType;
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
public class TextResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private Response response;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId")
    private Question question;

    @Enumerated(EnumType.STRING)
    private TextType textType;

    private String value;

    /* 편의 메서드 */
    public TextResponse(Response response, Question question, TextType textType, String value){
        this.response = response;
        this.question = question;
        this.textType = textType;
        this.value = value;
    }
}
