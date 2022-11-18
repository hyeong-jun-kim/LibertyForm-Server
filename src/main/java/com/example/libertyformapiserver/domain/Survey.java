package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.dto.survey.patch.PatchSurveyReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "survey")
    private List<Response> responses = new ArrayList<>();

    private String code;

    private String name;

    private String description;

    private String thumbnailImg;

    private LocalDate expirationDate;

    // 편의 메서드

    public void generateCode(){
        this.code = RandomStringUtils.randomAlphanumeric(12);
    }

    public void changeThumbnailImg(String thumbnailImg){
        this.thumbnailImg = thumbnailImg;
    }

    public void update(PatchSurveyReq survey){
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.expirationDate = survey.getExpirationDate();
    }
}
