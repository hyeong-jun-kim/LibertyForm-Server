package com.example.libertyformapiserver.service.scheduler;

import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.repository.SurveyRepository;
import com.example.libertyformapiserver.service.FlaskService;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyScheduler {
    private final FlaskService flaskService;

    private final SurveyRepository surveyRepository;

    // 자정마다 마감된 설문을 체크하여 Flask 서버로 보냄 (워드 클라우드, 감정 분석)
    @Scheduled(cron = "0 0 0 * * *")
    public void surveyAnalysisScheduleTask(){
        LocalDate localDate = LocalDate.now();

        List<Survey> surveys = surveyRepository.findByExpirationDate(localDate);

        surveys.forEach(s -> flaskService.sendTextResponseToFlaskBySurveyId(s.getId()));
    }
}
