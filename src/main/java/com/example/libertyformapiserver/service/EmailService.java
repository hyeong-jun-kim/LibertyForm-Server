package com.example.libertyformapiserver.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.domain.SurveyManagement;
import com.example.libertyformapiserver.dto.email.EmailSenderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.THREAD_OVER_REQUEST;

@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {
    private final String BASE_PRIVATE_URL = "http://liberty-form.shop/psurvey/";
    private final String SUBJECT = "[LibertyForm] 설문지 발송 링크 안내입니다.";

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    // 설문지 발송 링크 이메일 전송
    public void sendSurveyManagementEmail(Survey survey, List<String> receivers){
        String content = getContent(survey.getName(), survey.getCode(), survey.getExpirationDate().toString());

        sendSES(SUBJECT, content, receivers);
    }

    // 설문 관리를 이용해서 설문 발송 이메일 보내기
    // 연락처에 있는 사람이 설문을 했는지 안했는지 추적할 수 있음
    public void sendSurveyManagementEmail(List<SurveyManagement> surveyManagements, List<String> receivers){
        for(int i = 0; i < surveyManagements.size(); i++){
            SurveyManagement surveyManagement = surveyManagements.get(i);

            Survey survey = surveyManagement.getSurvey();
            sendSES(SUBJECT, getContent(survey.getName(), survey.getCode(), surveyManagement.getExpiredDate().toString()), Collections.singletonList(receivers.get(i)));
        }
    }

    // AWS SES로 비동기를 통해 이메일 전송
    @Async
    public void sendSES(final String subject, final String content, final List<String> receivers){
        try{
            final EmailSenderDto senderDto = EmailSenderDto.builder()
                    .to(receivers)
                    .subject(subject)
                    .content(content)
                    .build();

            final SendEmailResult sendEmailResult = amazonSimpleEmailService
                    .sendEmail(senderDto.toSendRequestDto());

            sendingResultMustSuccess(sendEmailResult);

        }catch (TaskRejectedException e){
            throw new BaseException(THREAD_OVER_REQUEST);
        }
    }

    private void sendingResultMustSuccess(final SendEmailResult sendEmailResult){
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200){
            log.warn("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }

    private String getContent(String title, String code, String expiredDate){
        StringBuilder linkSb = new StringBuilder();
        linkSb.append(code);

        StringBuilder emailContentSb = new StringBuilder();
        emailContentSb.append("<!DOCTYPE html>");
        emailContentSb.append("<html>");
        emailContentSb.append("<head>");
        emailContentSb.append("</head>");
        emailContentSb.append("<body>");
        emailContentSb.append("</body>");
        emailContentSb.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 700px; height: 900px; border-top: 4px solid #29ABE2; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 50px; margin: 0 0 10px 3px;\">Liberty Form</span><br/>" +
                        "		<span style=\"color: #29ABE2\">" + title + " 설문지</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "	    설문 작성을 위한 링크를 알려드립니다.<br/>" +
                        "		해당 설문의 마감 기한은 " + "<strong style=\"color:#29ABE2\">"+expiredDate+"</strong>입니다. <br/>" +
                        "   <div style=\"width: 576px;height: 90px; margin-top: 50px; padding: 0 27px;color: #242424;font-size: 16px;font-weight: bold;background-color: #F9F9F9;vertical-align: middle;line-height: 90px;\">설문지 링크 : <strong style=\"font-style: normal;font-weight: bold;color: #29ABE2\">" + linkSb + "</strong></div>" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "		감사합니다.<br/>" +
                        "	<div style=\"border-top: 4px solid #29ABE2; margin: 40px auto; padding: 30px 0;\"></div>" +
                        " </div>");
        emailContentSb.append("</html>");
        String content = emailContentSb.toString();
        return content;
    }

}
