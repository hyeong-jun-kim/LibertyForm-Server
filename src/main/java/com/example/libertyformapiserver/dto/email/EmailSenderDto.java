package com.example.libertyformapiserver.dto.email;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmailSenderDto {
    // TODO 추후에 libertyform 계정 SES 인증 받으면 이메일 주소 바꾸기
    public static final String FROM_EMAIL = "geeksasaeng@gmail.com";

    public final List<String> to;

    private final String subject;

    private String content;

    @Builder
    public EmailSenderDto(final List<String> to, final String subject, final String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public SendEmailRequest toSendRequestDto(){
        final Destination destination = new Destination()
                .withToAddresses(this.to);

        final Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body()
                        .withHtml(createContent(this.content)));

        return new SendEmailRequest()
                .withSource(FROM_EMAIL)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(final String text){
        return new Content()
                .withCharset("UTF-8")
                .withData(text);
    }
}
