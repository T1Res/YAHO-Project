package com.njm.yaho.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.njm.yaho.service.util.EmailAuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class EmailAuthController {
	@Autowired
    private EmailAuthService emailAuthService;
	
	// 이메일 중복 확인
	@GetMapping("/check-duplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam String email) {
	    boolean exists = emailAuthService.emailExistsInUser(email);
	    if (exists) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
	    }
	    return ResponseEntity.ok("사용 가능한 이메일입니다.");
	}

	// 인증코드 전송
    @PostMapping("/send")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        emailAuthService.sendAuthCode(email);
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }
    
    // 테스트용
    @GetMapping("/send")
    public ResponseEntity<String> testSend(@RequestParam String email) {
        emailAuthService.sendAuthCode(email);
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }
    
    // 인증
    @PostMapping("/verify")
    public ResponseEntity<String> verify(
            @RequestParam String email,
            @RequestParam String code,
            HttpSession session) {

        try {
            boolean result = emailAuthService.verifyCode(email, code, session);
            return ResponseEntity.ok("인증에 성공했습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("인증코드가 일치하지 않습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.GONE).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();  // ✅ 콘솔에 예외 전체 로그 출력
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }
    
    // 만료
    @PostMapping("/expire")
    public ResponseEntity<String> expireEmail(@RequestParam String email) {
    	emailAuthService.deleteByEmail(email);  // 또는 서비스 통해 삭제
        return ResponseEntity.ok("인증 정보가 만료되어 삭제되었습니다.");
    }


}
