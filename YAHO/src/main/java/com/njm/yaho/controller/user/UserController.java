package com.njm.yaho.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.njm.yaho.domain.oracle.user.UserOCDTO;
import com.njm.yaho.mapper.oracle.user.UserMapperOC;
import com.njm.yaho.service.user.UserService;
import com.njm.yaho.service.util.CaptchaService;
import com.njm.yaho.service.util.MailService;

@RestController
@RequestMapping("/User")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapperOC userMapperOC;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private CaptchaService captchaService;
	
	@GetMapping("/find-id/send")
	public ResponseEntity<String> sendUserIdToEmail(@RequestParam String email) {
	    UserOCDTO user = userMapperOC.findByEmail(email);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일입니다.");
	    }

	    // 이메일 전송
	    mailService.sendUserIdEmail(email, user.getUserId());

	    return ResponseEntity.ok("아이디 전송 완료");
	}

	@PostMapping("/find-pw/reset")
	public ResponseEntity<String> resetPassword(@RequestParam String userId,
	                                             @RequestParam String email,
	                                             @RequestParam("captcha") String captchaToken) {
	    // ✅ 1. reCAPTCHA 검증
	    if (!captchaService.verifyCaptcha(captchaToken)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("reCAPTCHA 인증 실패");
	    }

	    try {
	        boolean result = userService.resetPassword(userId, email);
	        if (!result) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 회원 정보가 없습니다.");
	        }
	        return ResponseEntity.ok("임시 비밀번호 전송 완료");
	    } catch (IllegalStateException | IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
	    }
	}
}
