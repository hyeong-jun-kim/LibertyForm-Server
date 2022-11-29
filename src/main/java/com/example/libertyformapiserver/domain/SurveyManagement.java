package com.example.libertyformapiserver.domain;

import com.example.libertyformapiserver.config.domain.BaseEntity;
import com.example.libertyformapiserver.config.status.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class SurveyManagement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private String code;

    private LocalDate expiredDate;

    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus = ResponseStatus.PENDING;

    // 편의 메서드
    public SurveyManagement(Member member, Contact contact, Survey survey, LocalDate expiredTime){
        this.member = member;
        this.contact = contact;
        this.survey = survey;
        this.expiredDate = expiredTime;
        code = generateCode();
    }

    public String generateCode(){
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public void changeResponseStatusConfirm(){
        this.responseStatus = ResponseStatus.CONFIRM;
    }
}
