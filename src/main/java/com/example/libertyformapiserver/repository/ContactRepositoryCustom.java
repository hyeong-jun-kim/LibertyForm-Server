package com.example.libertyformapiserver.repository;

import static com.example.libertyformapiserver.domain.QContact.contact;
import static com.example.libertyformapiserver.domain.QMemberContact.memberContact;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Optional<Contact> findEmailWithJoinByMemberAndEmail(Member member, String email){
        return Optional.ofNullable(queryFactory
                .select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
//                .fetchJoin()
                .where(memberContact.member.eq(member).and(contact.email.eq(email)))
                .fetchFirst());
    }
}
