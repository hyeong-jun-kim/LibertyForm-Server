package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.MultipleChoiceResponse;
import com.example.libertyformapiserver.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleChoiceRepository extends JpaRepository<MultipleChoiceResponse, Long> {

}
