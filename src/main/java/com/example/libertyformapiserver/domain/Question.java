package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "multipleChoiceQuestion_id")
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @OneToOne
    @JoinColumn(name = "shortQuestion_id")
    private ShortQuestion shortQuestion;

    @OneToOne
    @JoinColumn(name = "longQuestion_id")
    private LongQuestion longQuestion;

    // 질문 순서
    private int sequence;

    private String content;

    private String backgroundImgUrl;

    private String questionImgUrl;

    private boolean isEssential;
}
