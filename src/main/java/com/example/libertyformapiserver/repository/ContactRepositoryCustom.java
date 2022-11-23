package com.example.libertyformapiserver.repository;

import static com.example.libertyformapiserver.domain.QContact.contact;
import static com.example.libertyformapiserver.domain.QMemberContact.memberContact;

import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.dto.contact.vo.ContactVO;
import com.example.libertyformapiserver.dto.contact.get.GetPagingContactsRes;
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
    public Optional<Contact> findContactWithJoinByMemberAndEmail(Member member, String email) {
        return Optional.ofNullable(queryFactory
                .select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
                .where(memberContact.member.eq(member).and(contact.email.eq(email)))
                .fetchFirst());
    }

    // 연락처 모두 불러오기
    public List<Contact> findContactsWithJoinByMember(Member member) {
        return queryFactory
                .select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
                .where(memberContact.member.eq(member)
                        .and(contact.status.eq(BaseStatus.ACTIVE)))
                .fetch();
    }

    // 연락처 페이징 처리
    public GetPagingContactsRes findPageContactsByMember(Pageable pageable, Member member, int currentPage) {
        List<Contact> contacts = queryFactory.select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
                .where(memberContact.member.eq(member)
                        .and(contact.status.eq(BaseStatus.ACTIVE)))
                .orderBy(contact.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 마지막 페이지인지 확인을 위해 11개를 가져온다.
                .fetch();

        boolean isPrevMove = currentPage == 1 ? false : true;

        boolean isNextMove = false;

        if (contacts.size() == pageable.getPageSize() + 1) { // 11개면 다음 페이지가 존재 함
            contacts.remove(pageable.getPageSize()); // 마지막 인덱스 지우기
            isNextMove = true;
        }

        return new GetPagingContactsRes(ContactVO.toListDto(contacts), currentPage, isPrevMove, isNextMove);
    }

    // 연락처 검색 페이징 처리
    public GetPagingContactsRes findPageContactsByMemberAndKeyword(Pageable pageable, Member member, String keyword, int currentPage) {
        List<Contact> contacts = queryFactory.select(contact)
                .from(memberContact)
                .join(memberContact.contact, contact)
                .where(memberContact.member.eq(member)
                        .and(contact.name.contains(keyword))
                        .and(contact.status.eq(BaseStatus.ACTIVE)))
                .orderBy(contact.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // 마지막 페이지인지 확인을 위해 11개를 가져온다.
                .fetch();

        boolean isPrevMove = currentPage == 1 ? false : true;

        boolean isNextMove = false;

        if (contacts.size() == pageable.getPageSize() + 1) { // 11개면 다음 페이지가 존재 함
            contacts.remove(pageable.getPageSize()); // 마지막 인덱스 지우기
            isNextMove = true;
        }

        return new GetPagingContactsRes(ContactVO.toListDto(contacts), currentPage, isPrevMove, isNextMove);
    }
}
