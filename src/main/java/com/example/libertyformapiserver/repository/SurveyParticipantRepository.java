package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.SurveyParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyParticipantRepository extends JpaRepository<SurveyParticipant, Long> {

}
