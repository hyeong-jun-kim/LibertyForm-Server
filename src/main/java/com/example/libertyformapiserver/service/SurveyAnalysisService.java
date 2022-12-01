package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.config.type.TextType;
import com.example.libertyformapiserver.domain.*;
import com.example.libertyformapiserver.dto.question.post.PostQuestionRes;
import com.example.libertyformapiserver.dto.response.vo.ChoiceResponseVO;
import com.example.libertyformapiserver.dto.response.vo.NumericResponseVO;
import com.example.libertyformapiserver.dto.response.vo.TextResponseVO;
import com.example.libertyformapiserver.dto.surveyAnalysis.get.GetSurveyAnalysisRes;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_EXIST_SURVEY;
import static com.example.libertyformapiserver.config.response.BaseResponseStatus.NOT_MATCH_SURVEY;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SurveyAnalysisService {
    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final TextResponseRepository textResponseRepository;
    private final NumericResponseRepository numericResponseRepository;
    private final ChoiceRepositoryCustom choiceRepositoryCustom;
    private final SingleChoiceResponseRepository singleChoiceResponseRepository;
    private final MultipleChoiceResponseRepository multipleChoiceResponseRepository;
    private final MultipleChoiceRepository multipleChoiceRepository;

    public GetSurveyAnalysisRes getSurveyAnalysis(long surveyId, long memberId){
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_SURVEY));

        if(survey.getMember().getId() != memberId){
            throw new BaseException(NOT_MATCH_SURVEY);
        }

        GetSurveyAnalysisRes analysisRes = getAllResponse(survey); // 응답 결과 받아오기

        analysisRes = getSurveyResponse(analysisRes, survey); // 설문 정보 받아오기

        Long responseCnt = responseRepository.countBySurveyId(surveyId); // 총 응답수 저장
        analysisRes.setResponseCnt(responseCnt);

        return analysisRes;
    }


    /**
     * Flask 연동 관련 함수
     */
    // 워드 클라우드 S3 업로드
    public void uploadWordCloudFile(TextType textType, long surveyId){
    }

    /**
     * 편의 메서드
     */
    // 모든 응답들 가져오기
    public GetSurveyAnalysisRes getAllResponse(Survey survey){
        GetSurveyAnalysisRes analysisRes = new GetSurveyAnalysisRes();

        List<Question> questions = questionRepository.findQuestionsBySurveyIdAndStatus(survey.getId(), BaseStatus.ACTIVE);

        for(int i = 0; i < questions.size(); i++){
            Question question = questions.get(i);
            int questionTypeId = (int) question.getQuestionType().getId();
            long q_id = question.getId();

            List<Choice> choices;
            List<Integer> responses;
            ChoiceResponseVO choiceResponseVO;
            switch(questionTypeId){
                case 1: case 2: // 단답형, 장문형
                    List<TextResponse> textResponses = textResponseRepository.findByQuestionId(q_id);
                    TextResponseVO textResponseVO = new TextResponseVO(textResponses.size(), PostQuestionRes.toDto(question));
                    textResponseVO.setResponses(textResponses);

                    analysisRes.addTextResponse(textResponseVO);
                    break;
                case 3: // 단일 선택
                    choices = choiceRepositoryCustom.findChoiceWithJoinSingleChoiceByQuestion(question);
                    responses = singleChoiceResponseRepository.findByQuestionId(question.getId())
                            .stream().map(c -> c.getChoice().getNumber()).collect(Collectors.toList());

                    choiceResponseVO = new ChoiceResponseVO(responses.size(), PostQuestionRes.toDto(question));
                    choiceResponseVO.setChoices(choices);
                    choiceResponseVO.setResponses(responses);

                    analysisRes.addChoiceResponse(choiceResponseVO);
                    break;
                case 4: // 복수 선택
                    List<Integer> multipleResponses = new ArrayList<>();

                    choices = choiceRepositoryCustom.findChoiceWithJoinSingleChoiceByQuestion(question);
                    List<MultipleChoiceResponse> multipleChoiceResponses = multipleChoiceResponseRepository.findByQuestionId(question.getId());

                    // 복수형 선택 문항 응답 결과 저장하기
                    multipleChoiceResponses.stream()
                            .forEach(mcr ->
                                multipleChoiceRepository.findByMultipleChoiceResponse(mcr)
                                        .stream()
                                        .forEach(mc -> multipleResponses.add(mc.getChoice().getNumber())));


                    choiceResponseVO = new ChoiceResponseVO(multipleResponses.size(), PostQuestionRes.toDto(question));
                    choiceResponseVO.setChoices(choices);
                    choiceResponseVO.setResponses(multipleResponses);

                    analysisRes.addChoiceResponse(choiceResponseVO);
                    break;
                case 5: case 6: // 감정 바, 선형 대수
                    List<NumericResponse> numericResponses = numericResponseRepository.findByQuestionId(q_id);

                    NumericResponseVO numericResponseVO = new NumericResponseVO(numericResponses.size(), PostQuestionRes.toDto(question));
                    numericResponseVO.setResponses(numericResponses);

                    analysisRes.addNumericResponse(numericResponseVO);
                    break;
            }
        }

        return analysisRes;
    }

    // 설문지 관련 정보 받아오기
    public GetSurveyAnalysisRes getSurveyResponse(GetSurveyAnalysisRes res, Survey survey){
        res.setTitle(survey.getName());
        res.setCreatedDate(survey.getCreatedAt());
        res.setExpiredDate(survey.getExpirationDate());
        return res;
    }
}
