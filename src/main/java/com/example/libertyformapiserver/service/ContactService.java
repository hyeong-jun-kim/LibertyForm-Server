package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.example.libertyformapiserver.dto.contact.get.GetContactRes;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactReq;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactRes;
import com.example.libertyformapiserver.repository.ContactRepository;
import com.example.libertyformapiserver.repository.ContactRepositoryCustom;
import com.example.libertyformapiserver.repository.MemberContactRepository;
import com.example.libertyformapiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {
    private final int PAGING_SIZE = 10;

    private final MemberRepository memberRepository;

    private final ContactRepository contactRepository;
    private final ContactRepositoryCustom contactRepositoryCustom;
    private final MemberContactRepository memberContactRepository;

    // 연락처 생성
    @Transactional(readOnly = false, rollbackFor = {Exception.class, BaseException.class})
    public PostCreateContactRes createContact(PostCreateContactReq postCreateContactReq, long memberId){
        String email = postCreateContactReq.getEmail();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(INVALID_MEMBER));
        if(member.getEmail().equals(email)) // 자기 자신의 이메일 등록 안됨
            throw new BaseException(NOT_ALLOW_EMAIL);

        if(contactRepositoryCustom.findEmailWithJoinByMemberAndEmail(member, email).isPresent()){
            throw new BaseException(ALREADY_REGISTER_EMAIL) ;
        }

        Member targetMember = memberRepository.findMemberByEmail(email).orElseGet(() -> null);

        Contact contact = new Contact(email, postCreateContactReq.getName(), postCreateContactReq.getRelationship());
        if(targetMember != null)
            contact.changeMember(targetMember);
        contactRepository.save(contact);

        MemberContact memberContact = new MemberContact(member, contact);
        memberContactRepository.save(memberContact);

        return PostCreateContactRes.toDto(contact);
    }

    // 설문 발송 대상자 불러오기
    public List<GetContactRes> getContactList(int cursor, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        List<MemberContact> memberContactList = member.getMemberContacts();
        if(memberContactList == null)
            throw new BaseException(NOT_EXIST_CONTACT);

        // 페이징 처리
        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));

        return contactRepositoryCustom.findAllByMember(member, paging);
    }

    // 연락처 삭제
    @Transactional(readOnly = false)
    public void deleteContact(String email, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        Contact contact = contactRepositoryCustom.findEmailWithJoinByMemberAndEmail(member, email)
                        .orElseThrow(() -> new BaseException(NOT_EXIST_CONTACT));

        contactRepository.delete(contact);
    }
}
