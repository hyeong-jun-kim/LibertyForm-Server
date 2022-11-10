package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.example.libertyformapiserver.dto.contact.post.PostContactReq;
import com.example.libertyformapiserver.dto.contact.post.PostContactRes;
import com.example.libertyformapiserver.repository.ContactRepository;
import com.example.libertyformapiserver.repository.MemberContactRepository;
import com.example.libertyformapiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {
    private final MemberRepository memberRepository;

    private final ContactRepository contactRepository;
    private final MemberContactRepository memberContactRepository;

    // 연락처 생성
    @Transactional(readOnly = false, rollbackFor = {Exception.class, BaseException.class})
    public PostContactRes createContact(PostContactReq postContactReq, long memberId){
        String email = postContactReq.getEmail();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(INVALID_MEMBER));
        if(member.getEmail().equals(email)) // 자기 자신의 이메일 등록 안됨
            throw new BaseException(NOT_ALLOW_EMAIL);

        if(contactRepository.findByEmail(email).isPresent()) // 연락처 중복 방지
            throw new BaseException(ALREADY_EXIST_EMAIL);

        Member targetMember = memberRepository.findMemberByEmail(email).orElseGet(() -> null);

        Contact contact = new Contact(email, postContactReq.getName(), postContactReq.getRelationship());
        if(targetMember != null)
            contact.changeMember(targetMember);
        contactRepository.save(contact);

        MemberContact memberContact = new MemberContact(member, contact);
        memberContactRepository.save(memberContact);

        return PostContactRes.toDto(contact);
    }
}
