package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.question.post.PostChoiceQuestionReq;
import com.example.libertyformapiserver.dto.survey.get.GetSurveyInfoRes;
import com.example.libertyformapiserver.dto.surveyManagement.post.PostSurveyManagementReq;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class SurveyManagementService {
    private final SurveyManagementRepository surveyManagementRepository;
    private final ContactRepositoryCustom contactRepositoryCustom;
    private final EmailService emailService;

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    // 설문 발송 대상자 새로 생성하기
    public void createSurveyManagement(PostSurveyManagementReq req, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        // 연락처 가져오기
        List<Contact> contacts = req.getEmails().stream()
                .map(e -> contactRepositoryCustom.findEmailWithJoinByMemberAndEmail(member, e)
                        .orElseThrow(() -> new BaseException(NOT_EXIST_CONTACT)))
                .collect(Collectors.toList());

        Survey survey = surveyRepository.findById(req.getSurveyId())
                .orElseThrow(() -> new BaseException(NOT_EXIST_SURVEY));

        if(survey.getMember().getId() != memberId)
            throw new BaseException(NOT_MATCH_SURVEY);

        List<SurveyManagement> surveyManagements = contacts.stream().map(c -> new SurveyManagement(c, survey, req.getExpiredDate()))
                .collect(Collectors.toList());

        // 설문 발송 대상자에게 이메일 발송
        emailService.sendSurveyManagementEmail(surveyManagements, req.getEmails());

        surveyManagementRepository.saveAll(surveyManagements);
    }

    // 설문 발송자가 메일에 적힌 설문 주소를 읽었을 경우
    public GetSurveyInfoRes readSurvey(String code){
        SurveyManagement surveyManagement = surveyManagementRepository.findByCode(code)
                .orElseThrow(() -> new BaseException(NOT_EXIST_CODE));

        Survey survey = surveyManagement.getSurvey();

        surveyManagement.changeResponseStatusConfirm();

        return getSurveyInfo(survey);
    }

    // 편의 메서드

    // 설문지 정보 가져오기
    private GetSurveyInfoRes getSurveyInfo(Survey survey) {
        long surveyId = survey.getId();

        List<Question> questions = questionRepository.findQuestionsBySurveyId(surveyId);
        List<PostChoiceQuestionReq> postChoiceQuestionReqList = new ArrayList<>();

        Iterator<Question> iter = questions.iterator(); // ConcurrentModificationException 방지를 위해 iterator 사용
        while (iter.hasNext()) {
            Question question = iter.next();
            long questionTypeId = question.getQuestionType().getId();

            if (questionTypeId == 3 || questionTypeId == 4) {
                long questionId = question.getId();
                List<Choice> choiceList = choiceRepository.findChoicesByQuestionId(questionId);
                postChoiceQuestionReqList.add(PostChoiceQuestionReq.toVO(question, choiceList));
                iter.remove();
            }
        }

        GetSurveyInfoRes getSurveyInfoRes = GetSurveyInfoRes.toDto(survey, questions);
        getSurveyInfoRes.setChoiceQuestions(postChoiceQuestionReqList);
        return getSurveyInfoRes;
    }
}