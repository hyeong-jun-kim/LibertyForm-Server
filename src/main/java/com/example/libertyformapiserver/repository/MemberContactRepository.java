package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.MemberContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberContactRepository extends JpaRepository<MemberContact, Long> {
}
