package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.domain.SurveyManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyManagementRepository extends JpaRepository<SurveyManagement, Long> {
    Optional<SurveyManagement> findByCode(String code);
    List<SurveyManagement> findByMemberIdAndStatus(long memberId, BaseStatus status);
}
