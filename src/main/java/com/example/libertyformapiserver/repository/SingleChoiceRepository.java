package com.example.libertyformapiserver.repository;


import com.example.libertyformapiserver.domain.SingleChoiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleChoiceRepository extends JpaRepository<SingleChoiceResponse, Long> {

}
