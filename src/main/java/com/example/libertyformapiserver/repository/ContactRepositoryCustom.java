package com.example.libertyformapiserver.repository;

import static com.example.libertyformapiserver.domain.QContact.contact;
import static com.example.libertyformapiserver.domain.QMemberContact.memberContact;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberContact findEmailWithJoinByMemberAndEmail(Member member, String email){
        return queryFactory
                .selectFrom(memberContact)
                .join(memberContact.contact, contact)
                .fetchJoin()
                .where(memberContact.member.eq(member).and(contact.email.eq(email)))
                .fetchFirst();
    }
}
