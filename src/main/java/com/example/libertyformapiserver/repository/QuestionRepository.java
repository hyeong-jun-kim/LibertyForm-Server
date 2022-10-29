package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Question;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
