package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Contact;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.domain.MemberContact;
import com.example.libertyformapiserver.dto.contact.get.GetContactsRes;
import com.example.libertyformapiserver.dto.contact.get.GetPagingContactsRes;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactReq;
import com.example.libertyformapiserver.dto.contact.post.create.PostCreateContactRes;
import com.example.libertyformapiserver.dto.contact.vo.ContactVO;
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

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {
    private final int PAGING_SIZE = 15;

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

        Contact contact = contactRepositoryCustom.findContactWithJoinByMemberAndEmail(member, email)
                .orElseGet(() -> null);

        if(contact != null){
            if(contact.getStatus() == BaseStatus.INACTIVE){ // 연락처가 이미 존재할 경우 INACTIVE -> ACTIVE
                contact.changeStatusActive();
                return PostCreateContactRes.toDto(contact);

            }else{ // 연락처가 이미 활성화 되어 있을경우
                throw new BaseException(ALREADY_REGISTER_EMAIL);
            }
        }

        Member targetMember = memberRepository.findMemberByEmail(email).orElseGet(() -> null);

        contact = new Contact(email, postCreateContactReq.getName(), postCreateContactReq.getRelationship());
        if(targetMember != null)
            contact.changeMember(targetMember);
        contactRepository.save(contact);

        MemberContact memberContact = new MemberContact(member, contact);
        memberContactRepository.save(memberContact);

        return PostCreateContactRes.toDto(contact);
    }

    // 설문 발송 대상자 불러오기
    public GetContactsRes getMyContacts(long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        List<MemberContact> memberContactList = member.getMemberContacts();
        if(memberContactList == null)
            throw new BaseException(NOT_EXIST_CONTACT);

        List<ContactVO> contacts = ContactVO.toListDto(contactRepositoryCustom.findContactsWithJoinByMember(member));

        return new GetContactsRes(contacts);
    }

    // 설문 발송 대상자 페이징 처리해서 불러오기
    public GetPagingContactsRes getMyPagingContacts(int currentPage, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        // 페이징 처리
        PageRequest paging = PageRequest.of(currentPage-1, PAGING_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));

        GetPagingContactsRes contactRes = contactRepositoryCustom.findPageContactsByMember(paging, member, currentPage);

        if(contactRes.getContacts().isEmpty() && currentPage != 1) // 페이지가 존재하지 않을 경우
            throw new BaseException(NOT_EXIST_PAGE);

        return contactRepositoryCustom.findPageContactsByMember(paging, member, currentPage);
    }

    // 설문 발송 대상자 검색 페이징 처리해서 불러오기
    public GetPagingContactsRes getMyPagingContactsByKeyword(int currentPage, String keyword, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        List<MemberContact> memberContactList = member.getMemberContacts();

        // 페이징 처리
        PageRequest paging = PageRequest.of(currentPage-1, PAGING_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));

        return contactRepositoryCustom.findPageContactsByMemberAndKeyword(paging, member, keyword, currentPage);
    }

    // 연락처 삭제
    @Transactional(readOnly = false)
    public void deleteContact(String email, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        Contact contact = contactRepositoryCustom.findContactWithJoinByMemberAndEmail(member, email)
                        .orElseThrow(() -> new BaseException(NOT_EXIST_CONTACT));

        contact.changeStatusInActive(); // 연락처 비활성화

        member.getMemberContacts().stream() // 연락처 조인테이블 비활성화
                .filter(mc -> mc.getContact().getId() == contact.getId()).findFirst()
                .orElseThrow(() -> new BaseException(NOT_EXIST_CONTACT))
                .changeStatusInActive();
    }
}
