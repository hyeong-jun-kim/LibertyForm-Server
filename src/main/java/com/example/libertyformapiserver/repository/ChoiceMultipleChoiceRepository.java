package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.MultipleChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceMultipleChoiceRepository extends JpaRepository<MultipleChoice, Long> {

}
