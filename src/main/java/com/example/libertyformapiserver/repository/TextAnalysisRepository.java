package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.TextAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextAnalysisRepository extends JpaRepository<TextAnalysis, Long> {
}
