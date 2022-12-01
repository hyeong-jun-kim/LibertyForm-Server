package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Response;
import com.example.libertyformapiserver.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Optional<Response> findBySurveyId(long surveyId);

    Long countBySurveyId(long surveyId);
}
