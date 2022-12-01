package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.config.type.TextType;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.vo.ChoiceNumberVO;
import com.example.libertyformapiserver.dto.response.post.*;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResponseService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    private final ResponseRepository responseRepository;
    private final TextResponseRepository textResponseRepository;
    private final NumericResponseRepository numericResponseRepository;
    private final SingleChoiceRepository singleChoiceRepository;
    private final MultipleChoiceResponseRepository multipleChoiceResponseRepository;
    private final MultipleChoiceRepository multipleChoiceRepository;
    private final SurveyParticipantRepository surveyParticipantRepository;

    // 피 설문자 응답 저장
    @Transactional(readOnly = false, rollbackFor = {Exception.class, BaseException.class})
    public PostResponseRes createResponse(PostResponseReq postResponseDto, Long memberId){ // TODO 질문 유형별 정확한 Validation 필요
        long surveyId = postResponseDto.getSurveyId();
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_SURVEY));

        Member member = null;
        if(memberId != null)
            member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(INVALID_MEMBER));


        Response response = new Response(survey, member);
        responseRepository.save(response);

        // LONG TEXT(단답형), SHORT TEXT(장문형)
        List<PostTextResponseReq> textResponseDtoList = postResponseDto.getTextResponse();
        List<TextResponse> textResponseList = saveTextResponse(textResponseDtoList, response);
        List<PostTextResponseRes> textResponseResList = PostTextResponseRes.toListDto(textResponseList);

        // EMOTION_BAR(감정 바), LINEAR_ALGEBRA(선형 대수)
        List<PostNumericResponseReq> numericResponseDtoList = postResponseDto.getNumericResponse();
        List<NumericResponse> numericResponseList = saveNumericResponse(numericResponseDtoList, response);
        List<PostNumericResponseRes> numericResponseRes = PostNumericResponseRes.toListDto(numericResponseList);

        // SingleChoice(단일 선택)
        List<PostSingleChoiceResponseReq> singleChoiceResponseDtoList = postResponseDto.getSingleChoiceResponse();
        List<SingleChoiceResponse> singleChoiceResponseList = saveSingleChoiceResponse(singleChoiceResponseDtoList, response);
        List<PostSingleChoiceResponseRes> singleChoiceResponseRes = PostSingleChoiceResponseRes.toListDto(singleChoiceResponseList);

        // MultipleChoice(복수 선택)
        List<PostMultipleChoiceResponseReq> multipleChoiceResponseDtoList = postResponseDto.getMultipleChoiceResponse();
        List<MultipleChoice> multipleChoiceResponseList = saveMultipleChoiceResponse(multipleChoiceResponseDtoList, response);
        List<PostMultipleChoiceResponseRes> multipleChoiceResponseResList = PostMultipleChoiceResponseRes.toListDto(multipleChoiceResponseList);

        // 설문 참여자에 저장
        if(memberId != null){
            SurveyParticipant surveyParticipant = new SurveyParticipant(survey, member);
            surveyParticipantRepository.save(surveyParticipant);
        }

        return new PostResponseRes(response.getId(), textResponseResList, numericResponseRes, singleChoiceResponseRes, multipleChoiceResponseResList);

    }

    private List<TextResponse> saveTextResponse(List<PostTextResponseReq> textResponseDtoList, Response response){
        ArrayList<TextResponse> textResponseList = new ArrayList<>();

        for(PostTextResponseReq textResponseDto: textResponseDtoList){

            int questionNumber = textResponseDto.getQuestionNumber();
            Question question = getExistQuestion(response.getSurvey(), questionNumber);

            checkMatchQuestionType(question, 1, 2);

            String value = textResponseDto.getValue();
            TextType textType = textResponseDto.getType();

            TextResponse textResponse = new TextResponse(response, question, textType, value);
            textResponseList.add(textResponse);
        }
        textResponseRepository.saveAll(textResponseList);

        return textResponseList;
    }

    private List<NumericResponse> saveNumericResponse(List<PostNumericResponseReq> textNumericResponseDtoList, Response response){
        ArrayList<NumericResponse> numericResponseList = new ArrayList<>();

        for(PostNumericResponseReq numericResponseDto: textNumericResponseDtoList){
            int questionNumber = numericResponseDto.getQuestionNumber();
            Question question = getExistQuestion(response.getSurvey(), questionNumber);

            checkMatchQuestionType(question, 5, 6);

            int value = numericResponseDto.getValue();
            NumericType numericType = numericResponseDto.getType();

            NumericResponse NumericResponse = new NumericResponse(response, question, numericType, value);
            numericResponseList.add(NumericResponse);
        }
        numericResponseRepository.saveAll(numericResponseList);

        return numericResponseList;
    }

    private List<SingleChoiceResponse> saveSingleChoiceResponse(List<PostSingleChoiceResponseReq> singleChoiceResponseDtoList, Response response){
        ArrayList<SingleChoiceResponse> singleChoiceResponseList = new ArrayList<>();

        for(PostSingleChoiceResponseReq singleChoiceResponseDto: singleChoiceResponseDtoList){
            int questionNumber = singleChoiceResponseDto.getQuestionNumber();
            Question question = getExistQuestion(response.getSurvey(), questionNumber);

            checkMatchQuestionType(question, 3);

            int choiceNumber = singleChoiceResponseDto.getChoiceNumber();
            Choice choice = getExistChoice(question, choiceNumber);

            SingleChoiceResponse singleChoiceResponse = new SingleChoiceResponse(response, question, choice);

            singleChoiceResponseList.add(singleChoiceResponse);
        }
        singleChoiceRepository.saveAll(singleChoiceResponseList);

        return singleChoiceResponseList;
    }

    private List<MultipleChoice> saveMultipleChoiceResponse(List<PostMultipleChoiceResponseReq> multipleChoiceResponseDtoList, Response response){
        ArrayList<MultipleChoiceResponse> multipleChoiceResponseList = new ArrayList<>();
        ArrayList<MultipleChoice> choiceMultipleChoiceResponseList = new ArrayList<>();

        for(PostMultipleChoiceResponseReq multipleChoiceResponseDto: multipleChoiceResponseDtoList){
            int questionNumber = multipleChoiceResponseDto.getQuestionNumber();
            Question question = getExistQuestion(response.getSurvey(), questionNumber);

            checkMatchQuestionType(question, 4);
            
            List<ChoiceNumberVO> choiceList = multipleChoiceResponseDto.getChoices();
            List<Choice> choices = getExistChoiceList(question, choiceList);

            MultipleChoiceResponse multipleChoiceResponse = new MultipleChoiceResponse(response, question);
            multipleChoiceResponseList.add(multipleChoiceResponse);

            for(Choice choice: choices){
                MultipleChoice choiceMultipleChoiceResponse = new MultipleChoice(multipleChoiceResponse, choice);

                choiceMultipleChoiceResponseList.add(choiceMultipleChoiceResponse);
            }

        }
        multipleChoiceResponseRepository.saveAll(multipleChoiceResponseList);
        multipleChoiceRepository.saveAll(choiceMultipleChoiceResponseList);

        return choiceMultipleChoiceResponseList;
    }

    private Question getExistQuestion(Survey survey, int questionNumber){
        return questionRepository.findQuestionBySurveyAndNumber(survey, questionNumber)
                .orElseThrow(() -> new BaseException(NOT_EXIST_QUESTION));
    }

    private Choice getExistChoice(Question question, int choiceNumber){
        return choiceRepository.findChoiceByQuestionAndNumber(question, choiceNumber)
                .orElseThrow(() -> new BaseException(NOT_EXIST_CHOICE));
    }

    private List<Choice> getExistChoiceList(Question question, List<ChoiceNumberVO> choices){
        return choices.stream().map(c -> choiceRepository.findChoiceByQuestionAndNumber(question, c.getChoiceNumber())
                        .orElseThrow(() -> new BaseException(NOT_EXIST_CHOICE))).collect(Collectors.toList());
    }

    private void checkMatchQuestionType(Question question, int ...typeNum){
        long questionTypeId = question.getQuestionType().getId();

        for(int i = 0; i < typeNum.length; i++){
            if(questionTypeId == typeNum[i])
                return;
        }

        // 일치하는 질문 유형이 없으면 예외처리
        throw new BaseException(NOT_MATCH_QUESTION_TYPE);
    }
}
