package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByMemberAndEmail(Member Member, String email);
}
