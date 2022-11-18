package com.example.libertyformapiserver.repository;

import static com.example.libertyformapiserver.domain.QContact.contact;
import static com.example.libertyformapiserver.domain.QMemberContact.memberContact;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.example.libertyformapiserver.dto.contact.get.GetContactRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    // 연락처 중복 방지, 연락처 찾기 용
    public Optional<Contact> findEmailWithJoinByMemberAndEmail(Member member, String email){
        return Optional.ofNullable(queryFactory
                .select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
//                .fetchJoin()
                .where(memberContact.member.eq(member).and(contact.email.eq(email)))
                .fetchFirst());
    }

    // 연락처 페이징 처리
    public List<GetContactRes> findAllByMember(Member member, Pageable pageable){
        List<Contact> contacts =queryFactory.select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
                .where(memberContact.member.eq(member))
                .orderBy(contact.name.asc())
                // TODO fetchJoin test
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 마지막 페이지인지 확인을 위해 11개를 가져온다.
                .fetch();

        boolean isFinalPage = true;

        if(contacts.size() == pageable.getPageSize() + 1){ // 11개면 다음 페이지가 존재 함
            contacts.remove(pageable.getPageSize()); // 마지막 인덱스 지우기
            isFinalPage = false;
        }

        return GetContactRes.toListDto(contacts);
    }
}
