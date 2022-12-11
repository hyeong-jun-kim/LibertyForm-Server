package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class TextAnalysis extends BaseEntity {
    @Id
    @Column(name = "question_id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "question_id")
    private Question question;

    private String wordCloudImgUrl;

    /**
     * 편의 메서드
     */
    public void changeWordCloudImgUrl(String url){
        this.wordCloudImgUrl = url;
    }
}
