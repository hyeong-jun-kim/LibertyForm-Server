package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findSurveysByMemberId(long memberId);
}
