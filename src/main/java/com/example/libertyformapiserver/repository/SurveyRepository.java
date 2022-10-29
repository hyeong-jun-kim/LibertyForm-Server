package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
