package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Response;
import com.example.libertyformapiserver.domain.TextResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextResponseRepository extends JpaRepository<TextResponse, Long> {

}
