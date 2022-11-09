package com.example.libertyformapiserver.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.dto.email.EmailSenderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.THREAD_OVER_REQUEST;

@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailService {
    private final AmazonSimpleEmailService amazonSimpleEmailService;

    // 설문지 발송 링크 이메일 전송
    // TODO 설문지 발송 관리 기능 할 때 DB 연결하기
    public void sendSurveyEmail(Survey survey, List<String> receivers){
        String subject = "[LibertyForm] 설문지 발송 링크 안내입니다.";
        String content = getContent(survey.getCode(), survey.getExpirationDate().toString());

        sendSES(subject, content, receivers);
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

    // TODO 추후에 리액트 VM에 올라가면 surveyLink 반영하기
    private String getContent(String surveyLink, String expiredDate){
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<!DOCTYPE html>");
        emailContent.append("<html>");
        emailContent.append("<head>");
        emailContent.append("</head>");
        emailContent.append("<body>");
        emailContent.append("</body>");
        emailContent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 700px; height: 900px; border-top: 4px solid #29ABE2; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 50px; margin: 0 0 10px 3px;\">Liberty Form</span><br/>" +
                        "		<span style=\"color: #29ABE2\">설문지 발송</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "	    설문 작성을 위한 링크를 알려드립니다.<br/>" +
                        "		해당 설문의 마감 기한은 " + "<strong style=\"color:#29ABE2\">"+expiredDate+"</strong>입니다.. <br/>" +
                        "   <div style=\"width: 576px;height: 90px; margin-top: 50px; padding: 0 27px;color: #242424;font-size: 16px;font-weight: bold;background-color: #F9F9F9;vertical-align: middle;line-height: 90px;\">설문지 링크 : <strong style=\"font-style: normal;font-weight: bold;color: #29ABE2\">" + surveyLink + "</strong></div>" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "		감사합니다.<br/>" +
                        "	<div style=\"border-top: 4px solid #29ABE2; margin: 40px auto; padding: 30px 0;\"></div>" +
                        " </div>");
        emailContent.append("</html>");
        String content = emailContent.toString();
        return content;
    }

}
