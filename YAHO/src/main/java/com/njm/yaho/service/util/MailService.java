package com.njm.yaho.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class MailService {
	@Autowired
    private JavaMailSender mailSender;

    /**
     * 인증 코드를 포함한 메일을 전송합니다.
     *
     * @param toEmail 수신자 이메일
     * @param authCode 인증 코드 (6자리)
     */
    public void sendAuthEmail(String toEmail, String authCode) {
        String subject = "[YAHO] 이메일 인증 코드 안내";
        String text = String.format(
                "안녕하세요!\n\n" +
                "YAHO에 가입해 주셔서 감사합니다.\n\n" +
                "아래 인증 코드를 회원가입 페이지에 입력해 주세요:\n\n" +
                "🔐 인증 코드: %s\n\n" +
                "⏰ 인증 코드는 10분 동안 유효합니다.", authCode);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("YAHO Support <jsl.yaho.service@gmail.com>");

        mailSender.send(message);
    }
}
