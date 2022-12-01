package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.SingleChoiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SingleChoiceResponseRepository extends JpaRepository<SingleChoiceResponse, Long> {
    List<SingleChoiceResponse> findByQuestionId(long questionId);
}
