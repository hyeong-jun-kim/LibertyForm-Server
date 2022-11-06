package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.config.type.NumericType;
import com.example.libertyformapiserver.config.type.TextType;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.choice.vo.ChoiceVO;
import com.example.libertyformapiserver.dto.response.post.*;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_EXIST_CHOICE;
import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_EXIST_QUESTION;

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
    private final MultipleChoiceRepository multipleChoiceRepository;
    private final ChoiceMultipleChoiceRepository choiceMultipleChoiceRepository;

    // 피 설문자 응답 저장
    @Transactional(readOnly = false, rollbackFor = {Exception.class, BaseException.class})
    public PostResponseRes createResponse(PostResponseReq postResponseDto, Long memberId){
        long surveyId = postResponseDto.getSurveyId();
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_SURVEY));

        Member member = memberRepository.findById(memberId).orElseGet(null);

        Response response = new Response(survey, member);

        List<PostTextResponseReq> textResponseDtoList = postResponseDto.getTextResponse();
        List<TextResponse> textResponseList = saveTextResponse(textResponseDtoList, response);
        List<PostTextResponseRes> textResponseResList = PostTextResponseRes.toListDto(textResponseList);

        List<PostNumericResponseReq> numericResponseDtoList = postResponseDto.getNumericResponse();
        List<NumericResponse> numericResponseList = saveNumericResponse(numericResponseDtoList, response);
        List<PostNumericResponseRes> numericResponseRes = PostNumericResponseRes.toListDto(numericResponseList);

        List<PostSingleChoiceResponseReq> singleChoiceResponseDtoList = postResponseDto.getSingleChoiceResponse();
        List<SingleChoiceResponse> singleChoiceResponseList = saveSingleChoiceResponse(singleChoiceResponseDtoList, response);
        List<PostSingleChoiceResponseRes> singleChoiceResponseRes = PostSingleChoiceResponseRes.toListDto(singleChoiceResponseList);

        List<PostMultipleChoiceResponseReq> multipleChoiceResponseDtoList = postResponseDto.getMultipleChoiceResponse();
        List<Choice_MultipleChoice_Response> multipleChoiceResponseList = saveMultipleChoiceResponse(multipleChoiceResponseDtoList, response);
        List<PostMultipleChoiceResponseRes> multipleChoiceResponseResList = PostMultipleChoiceResponseRes.toListDto(multipleChoiceResponseList);

        return new PostResponseRes(textResponseResList, numericResponseRes, singleChoiceResponseRes, multipleChoiceResponseResList);

    }

    private List<TextResponse> saveTextResponse(List<PostTextResponseReq> textResponseDtoList, Response response){
        ArrayList<TextResponse> textResponseList = new ArrayList<>();

        for(PostTextResponseReq textResponseDto: textResponseDtoList){
            long questionId = textResponseDto.getQuestionId();
            Question question = getExistQuestion(questionId);

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
            long questionId = numericResponseDto.getQuestionId();
            Question question = getExistQuestion(questionId);

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
            long questionId = singleChoiceResponseDto.getQuestionId();
            Question question = getExistQuestion(questionId);

            long choiceId = singleChoiceResponseDto.getChoiceId();
            Choice choice = getExistChoice(choiceId);

            SingleChoiceResponse singleChoiceResponse = new SingleChoiceResponse(response, question, choice);

            singleChoiceResponseList.add(singleChoiceResponse);
        }
        singleChoiceRepository.saveAll(singleChoiceResponseList);

        return singleChoiceResponseList;
    }

    private List<Choice_MultipleChoice_Response> saveMultipleChoiceResponse(List<PostMultipleChoiceResponseReq> multipleChoiceResponseDtoList, Response response){
        ArrayList<MultipleChoiceResponse> multipleChoiceResponseList = new ArrayList<>();
        ArrayList<Choice_MultipleChoice_Response> choiceMultipleChoiceResponseList = new ArrayList<>();

        for(PostMultipleChoiceResponseReq multipleChoiceResponseDto: multipleChoiceResponseDtoList){
            long questionId = multipleChoiceResponseDto.getQuestionId();
            Question question = getExistQuestion(questionId);

            List<ChoiceVO> choiceList = multipleChoiceResponseDto.getChoices();
            List<Choice> choices = getExistChoiceList(choiceList);

            MultipleChoiceResponse multipleChoiceResponse = new MultipleChoiceResponse(response, question);
            multipleChoiceResponseList.add(multipleChoiceResponse);

            for(Choice choice: choices){
                Choice_MultipleChoice_Response choiceMultipleChoiceResponse = new Choice_MultipleChoice_Response(multipleChoiceResponse, choice);

                choiceMultipleChoiceResponseList.add(choiceMultipleChoiceResponse);
            }

        }
        multipleChoiceRepository.saveAll(multipleChoiceResponseList);
        choiceMultipleChoiceRepository.saveAll(choiceMultipleChoiceResponseList);

        return choiceMultipleChoiceResponseList;
    }

    private Question getExistQuestion(long questionId){
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_QUESTION));
    }

    private Choice getExistChoice(long choiceId){
        return choiceRepository.findById(choiceId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_CHOICE));
    }

    private List<Choice> getExistChoiceList(List<ChoiceVO> choices){
        List<Choice> choiceList =
                choices.stream().map(c -> choiceRepository.findById(c.getChoiceId())
                        .orElseThrow(() -> new BaseException(NOT_EXIST_CHOICE))).collect(Collectors.toList());

        return choiceList;
    }
}
