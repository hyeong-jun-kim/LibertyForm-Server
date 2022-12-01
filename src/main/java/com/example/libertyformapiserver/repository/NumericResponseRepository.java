package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.Response;
import com.example.libertyformapiserver.domain.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NumericResponseRepository extends JpaRepository<NumericResponse, Long> {
    List<NumericResponse> findByQuestionId(long questionId);

}
