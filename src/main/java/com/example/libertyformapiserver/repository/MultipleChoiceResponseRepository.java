package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.MultipleChoice;
import com.example.libertyformapiserver.domain.MultipleChoiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MultipleChoiceResponseRepository extends JpaRepository<MultipleChoiceResponse, Long> {
    List<MultipleChoiceResponse> findByQuestionId(long questionId);

}
