package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findChoicesByQuestionId(long questionId);
    Optional<Choice> findChoiceByQuestionAndNumber(Question question, int number);
}
