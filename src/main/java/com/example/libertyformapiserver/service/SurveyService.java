package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceRes;
import com.example.libertyformapiserver.dto.question.post.PostChoiceQuestionReq;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyReq;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyRes;
import com.example.libertyformapiserver.dto.survey.post.PostSurveyReq;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SurveyService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionTypeRepository questionTypeRepository;

    // 설문지 생성
    @Transactional(readOnly = false)
    public PostCreateSurveyRes createSurvey(PostCreateSurveyReq surveyReqDto){
        PostSurveyReq postSurveyReq = surveyReqDto.getSurvey();

        Member member = memberRepository.findById(1L) // TODO 추후에 JWT로 받아오기
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));
        Survey survey = postSurveyReq.toEntity(member);
        surveyRepository.save(survey);

        PostCreateSurveyRes createSurveyResDto = new PostCreateSurveyRes(survey);

        List<PostQuestionReq> postQuestionReqList = surveyReqDto.getQuestions();
        List<PostChoiceQuestionReq> postChoiceQuestionReqList = surveyReqDto.getChoiceQuestions();

        List<PostQuestionRes> questionResList = getQuestionListEntity(postQuestionReqList, survey);
        createSurveyResDto.setQuestions(questionResList);

        createSurveyResDto = getChoiceQuestionListEntity(postChoiceQuestionReqList, createSurveyResDto, survey);

        return createSurveyResDto;
    }

    /*
     * 편의 메서드
     */
    // 주관식, 감정 바, 선형 대수 질문 저장
    private List<PostQuestionRes> getQuestionListEntity(List<PostQuestionReq> postQuestionReqList, Survey survey){
        List<PostQuestionRes> questionResList = new ArrayList<>();

        // Question 에 Survey, QuestionType 넣기
        for(int i = 0; i < postQuestionReqList.size(); i++){
            PostQuestionReq postQuestionReq = postQuestionReqList.get(i);

            long questionTypeId = postQuestionReq.getQuestionTypeId();

            // 객관식 유형으로 들어왔을 때 예외 처리
            if(questionTypeId == 3 || questionTypeId == 4)
                throw new BaseException(NOT_MATCH_QUESTION_TYPE);

            QuestionType questionType = questionTypeRepository.findById(questionTypeId).orElseThrow(
                    () -> new BaseException(NOT_VALID_QUESTION_TYPE));


            Question question = postQuestionReq.toEntity(survey, questionType);
            question.changeStatusActive();

            questionResList.add(PostQuestionRes.toDto(question));

            checkQuestionNumber(questionResList.size(), question.getNumber());

            questionRepository.save(question);
        }
        return questionResList;
    }

    // 객관식 질문 저장
    private PostCreateSurveyRes getChoiceQuestionListEntity(List<PostChoiceQuestionReq> postChoiceQuestionReqList, PostCreateSurveyRes surveyResDto, Survey survey){
        if(postChoiceQuestionReqList == null)
            return surveyResDto;

        List<PostQuestionRes> questionResList = surveyResDto.getQuestions();
        List<PostChoiceRes> choiceResList = new ArrayList<>();

        // 객관식 문항 저장
        for(int i = 0; i < postChoiceQuestionReqList.size(); i++){
            PostChoiceQuestionReq choiceQuestionReq = postChoiceQuestionReqList.get(i);
            PostQuestionReq questionReq = choiceQuestionReq.getQuestion();

            long questionTypeId = questionReq.getQuestionTypeId();

            // 주관식 유형으로 들어왔을 때 예외 처리
            if(questionTypeId != 3 && questionTypeId != 4)
                throw new BaseException(NOT_MATCH_QUESTION_TYPE);

            QuestionType questionType = questionTypeRepository.findById(questionTypeId).orElseThrow(
                    () -> new BaseException(NOT_VALID_QUESTION_TYPE));


            Question question = questionReq.toEntity(survey, questionType);
            question.changeStatusActive();

            questionResList.add(PostQuestionRes.toDto(question));

            checkQuestionNumber(questionResList.size(), question.getNumber());

            questionRepository.save(question);

            // 객관식 문항 저장
            List<PostChoiceReq> postChoiceReqList = choiceQuestionReq.getChoices();
            for(int j = 0; j < postChoiceReqList.size(); j++){
                PostChoiceReq postChoiceReq = postChoiceReqList.get(j);

                Choice choice = postChoiceReq.toEntity(question);
                choice.changeStatusActive();

                choiceResList.add(PostChoiceRes.toDto(choice));

                checkQuestionNumber(j+1, choice.getNumber());

                choiceRepository.save(choice);
            }
        }
        surveyResDto.setQuestions(questionResList);
        surveyResDto.setChoices(choiceResList);

        return surveyResDto;
    }

    // 설문 문항 번호가 올바른지 체크
    private void checkQuestionNumber(int i, int num){
        if(i != num)
            throw new BaseException(NOT_SEQUENCE_QUESTION_NUMBER);
    }
}
