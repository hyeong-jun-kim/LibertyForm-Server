package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
