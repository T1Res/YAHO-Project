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
    
	/**
	 * 사용자 아이디를 포함한 메일을 전송합니다.
	 *
	 * @param toEmail 수신자 이메일
	 * @param userId  사용자 아이디
	 */
    public void sendUserIdEmail(String toEmail, String userId) {
        String subject = "[YAHO] 회원 아이디 안내";
        String text = String.format(
            "안녕하세요.\n\n" +
            "요청하신 이메일로 등록된 회원님의 아이디는 아래와 같습니다:\n\n" +
            "🆔 아이디: %s\n\n" +
            "감사합니다.\n- YAHO 운영팀", userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("YAHO Support <jsl.yaho.service@gmail.com>");
        mailSender.send(message);
    }
    
    /**
    * 임시 비밀번호를 포함한 메일을 전송합니다.
    * @param toEmail
    * @param tempPassword
    */
    public void sendTempPasswordEmail(String toEmail, String tempPassword) {
        String subject = "[YAHO] 임시 비밀번호 안내";
        String text = String.format(
            "안녕하세요.\n\n" +
            "비밀번호 찾기 요청에 따라 임시 비밀번호를 발급해 드립니다:\n\n" +
            "🔐 임시 비밀번호: %s\n\n" +
            "로그인 후 반드시 비밀번호를 변경해 주세요.\n\n" +
            "감사합니다.\nYAHO 운영팀", tempPassword
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("YAHO Support <jsl.yaho.service@gmail.com>");
        mailSender.send(message);
    }
}
