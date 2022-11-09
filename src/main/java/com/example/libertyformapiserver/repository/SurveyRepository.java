package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findByIdAndStatus(long surveyId, BaseStatus status);
    Optional<Survey> findByCodeAndStatus(String code, BaseStatus status);
    List<Survey> findSurveysByMemberIdAndStatus(long memberId, BaseStatus status);
}
