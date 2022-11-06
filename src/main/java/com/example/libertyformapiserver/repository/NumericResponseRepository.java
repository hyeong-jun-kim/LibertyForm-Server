package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.NumericResponse;
import com.example.libertyformapiserver.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumericResponseRepository extends JpaRepository<NumericResponse, Long> {

}
