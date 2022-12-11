package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.domain.TextResponse;
import com.example.libertyformapiserver.dto.flask.post.PostWordCloudDto;
import com.example.libertyformapiserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FlaskService {
    private final String FLASK_BASE_URL = "http://121.163.214.192:5000";

    private final MemberRepository memberRepository;

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    private final ObjectStorageService objectStorageService;

    private final TextResponseRepository textResponseRepository;

    private final RestTemplateService<String> restTemplateService;

    // 단답형, 장문형 응답 리스트 플라스크 서버로 보내기
    @Async
    public void sendTextResponse(long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_MATCH_SURVEY));

        List<Question> questions = survey.getQuestions();

        for(Question question: questions){
            if(question.getQuestionType().getId() == 1){
                sendLongTextResponse(question);

            }else if(question.getQuestionType().getId() == 2){
                sendShortTextResponse(question);
            }
        }
    }

    // 장문형 응답 리스트 Flask로 보내기
    private void sendLongTextResponse(Question question) {
        HashMap<Long, String> responseMap = new HashMap<>();

        List<TextResponse> textResponses = textResponseRepository.findByQuestionId(question.getId());
        if(textResponses.isEmpty())
            return;

        PostWordCloudDto longTextDto = new PostWordCloudDto(textResponses.stream().map(t -> t.getValue()).collect(Collectors.toList()));
        textResponses.forEach(t -> responseMap.put(t.getId(), t.getValue()));

        final String WORLD_CLOUD_LONG_URL = FLASK_BASE_URL + "/wordcloud/long/" + question.getId();
        final String EMOTION_ANALYSIS_URL = FLASK_BASE_URL + "/emotion";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 워드 클라우드
        restTemplateService.post(WORLD_CLOUD_LONG_URL, HttpHeaders.EMPTY, longTextDto, String.class);

        // 감정 분석
        restTemplateService.post(EMOTION_ANALYSIS_URL, HttpHeaders.EMPTY, responseMap, String.class);
    }

    // 단답형 응답 리스트 Flask로 보내기
    private void sendShortTextResponse(Question question) {
        List<TextResponse> textResponses = textResponseRepository.findByQuestionId(question.getId());
        if(textResponses.isEmpty())
            return;

        PostWordCloudDto shortTextDto = new PostWordCloudDto(textResponses.stream().map(t -> t.getValue()).collect(Collectors.toList()));

        final String WORLD_CLOUD_SHORT_URL = FLASK_BASE_URL + "/wordcloud/short/" + question.getId();

        // 워드 클라우드
        restTemplateService.post(WORLD_CLOUD_SHORT_URL, null, shortTextDto);
    }
}
