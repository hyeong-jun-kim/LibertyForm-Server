package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.dto.question.patch.PatchQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices = new ArrayList<>();

    private int number;

    private String name;

    private String description;

    private String questionImgUrl;

    private boolean answerRequired;

    // 편의 메서드
    public void changeQuestionImgUrl(String questionImgUrl){
        this.questionImgUrl = questionImgUrl;
    }

    public void update(PatchQuestionReq question){
        this.questionType.changeQuestionType(question.getQuestionTypeId());
        this.name = question.getName();
        this.number = question.getNumber();
        this.description = question.getDescription();
        this.answerRequired = question.isAnswerRequired();
        changeStatusActive();
    }
}
