package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceRes;
import com.example.libertyformapiserver.dto.question.vo.ChoiceQuestionVO;
import com.example.libertyformapiserver.dto.choice.post.PostChoiceReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionReq;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyReq;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyRes;
import com.example.libertyformapiserver.dto.survey.get.GetListSurveyRes;
import com.example.libertyformapiserver.dto.survey.get.GetSurveyInfoRes;
import com.example.libertyformapiserver.dto.survey.post.PostSurveyReq;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    private final ObjectStorageService objectStorageService;

    // 설문지 생성
    @Transactional(readOnly = false, rollbackFor = {Exception.class, BaseException.class})
    public PostCreateSurveyRes createSurvey(PostCreateSurveyReq surveyReqDto, long memberId
            , MultipartFile thumbnailImgFile, List<MultipartFile> questionImgFiles){
        PostSurveyReq postSurveyReq = surveyReqDto.getSurvey();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER));

        Survey survey = postSurveyReq.toEntity(member);
        survey.changeStatusActive();
        surveyRepository.save(survey);

        PostCreateSurveyRes createSurveyResDto = new PostCreateSurveyRes(survey);

        // 주관식, 객관식 문항 가져오기
        List<PostQuestionReq> postQuestionReqList = surveyReqDto.getQuestions();
        List<ChoiceQuestionVO> choiceQuestionVOList = surveyReqDto.getChoiceQuestions();

        // 주관식 문항 DTO -> Entity 변환
        List<PostQuestionRes> questionResList = getQuestionListEntity(postQuestionReqList, survey);
        createSurveyResDto.setQuestions(questionResList);

        // 섬네일, 설문 이미지 Object Storage에 업로드
        objectStorageService.uploadThumbnailImg(thumbnailImgFile);
        objectStorageService.uploadQuestionImgs(questionImgFiles);

        createSurveyResDto = getChoiceQuestionListEntity(choiceQuestionVOList, createSurveyResDto, survey);
        checkQuestionNumber(createSurveyResDto.getQuestions());
        return createSurveyResDto;
    }

    // 설문지 모두 조회
    public GetListSurveyRes getAllUserSurvey(long memberId){
        List<Survey> surveys = surveyRepository.findSurveysByMemberId(memberId);

        return GetListSurveyRes.listEntitytoDto(surveys);
    }

    // 단일 설문지 조회
    public GetSurveyInfoRes getSurveyInfo(long surveyId, long memberId){
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_SURVEY));

        if(survey.getMember().getId() != memberId) // 설문지 작성자가 해당 유저가 아닌 경우
            throw new BaseException(NOT_MATCH_SURVEY);

        List<Question> questions = questionRepository.findQuestionsBySurveyId(surveyId);
        List<ChoiceQuestionVO> choiceQuestionVOList = new ArrayList<>();

        Iterator<Question> iter = questions.iterator(); // ConcurrentModificationException 방지를 위해 iterator 사용
        while(iter.hasNext()){
            Question question = iter.next();
            long questionTypeId = question.getQuestionType().getId();

            if(questionTypeId == 3 || questionTypeId == 4){
                long questionId = question.getId();
                List<Choice> choiceList = choiceRepository.findChoicesByQuestionId(questionId);
                choiceQuestionVOList.add(ChoiceQuestionVO.toVO(question, choiceList));
                iter.remove();
            }
        }

        GetSurveyInfoRes getSurveyInfoRes = GetSurveyInfoRes.toDto(survey, questions);
        getSurveyInfoRes.setChoiceQuestions(choiceQuestionVOList);
        return getSurveyInfoRes;
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
            questionRepository.save(question);
        }
        return questionResList;
    }

    // 객관식 질문 저장
    private PostCreateSurveyRes getChoiceQuestionListEntity(List<ChoiceQuestionVO> choiceQuestionVOList, PostCreateSurveyRes surveyResDto, Survey survey){
        if(choiceQuestionVOList == null)
            return surveyResDto;

        List<PostQuestionRes> questionResList = surveyResDto.getQuestions();
        List<PostChoiceRes> choiceResList = new ArrayList<>();

        // 객관식 문항 저장
        for(int i = 0; i < choiceQuestionVOList.size(); i++){
            ChoiceQuestionVO choiceQuestionReq = choiceQuestionVOList.get(i);
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
            questionRepository.save(question);

            // 객관식 문항 저장
            List<PostChoiceReq> postChoiceReqList = choiceQuestionReq.getChoices();
            checkChoiceNumber(postChoiceReqList);

            for(int j = 0; j < postChoiceReqList.size(); j++){
                PostChoiceReq postChoiceReq = postChoiceReqList.get(j);

                Choice choice = postChoiceReq.toEntity(question);
                choice.changeStatusActive();

                choiceResList.add(PostChoiceRes.toDto(choice));
                choiceRepository.save(choice);
            }
        }
        surveyResDto.setQuestions(questionResList);
        surveyResDto.setChoices(choiceResList);

        return surveyResDto;
    }

    // 설문 문항 번호가 올바른지 체크
    private void checkQuestionNumber(List<PostQuestionRes> questionList){
        List<Integer> numberList = new ArrayList<>();
        questionList.forEach(q -> numberList.add(q.getNumber()));

        Collections.sort(numberList);

        for(int i = 0; i < numberList.size() - 1; i++){
            if(numberList.get(i+1) - numberList.get(i) != 1)
                throw new BaseException(NOT_SEQUENCE_QUESTION_NUMBER);
        }
    }

    // 객관식 문항 번호가 올바른지 체크
    private void checkChoiceNumber(List<PostChoiceReq> choiceList){
        List<Integer> numberList = new ArrayList<>();
        choiceList.forEach(q -> numberList.add(q.getNumber()));

        Collections.sort(numberList);

        for(int i = 0; i < numberList.size() - 1; i++){
            if(numberList.get(i+1) - numberList.get(i) != 1)
                throw new BaseException(NOT_SEQUENCE_QUESTION_NUMBER);
        }
    }
}
