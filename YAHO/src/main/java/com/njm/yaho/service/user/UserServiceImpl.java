package com.njm.yaho.service.user;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.njm.yaho.domain.oracle.user.UserOCDTO;
import com.njm.yaho.mapper.oracle.user.UserMapperOC;
import com.njm.yaho.service.util.MailService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapperOC userMapperOC;

    @Autowired
    private MailService mailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
	@Override
	public boolean resetPassword(String userId, String email) {
		int todayCount = userMapperOC.countTodayPwReset(userId);
	    if (todayCount >= 3) {
	        throw new IllegalStateException("하루 최대 3회까지만 요청할 수 있습니다.");
	    }

	    // 2. 2분 이내 요청 제한
	    Timestamp lastTime = userMapperOC.findLastPwResetTime(userId);
	    if (lastTime != null) {
	        long seconds = Duration.between(lastTime.toInstant(), Instant.now()).getSeconds();
	        if (seconds < 120) {
	            throw new IllegalArgumentException("2분 후에 다시 시도해 주세요.");
	        }
	    }

	    // 3. 유저 확인
	    UserOCDTO user = userMapperOC.findByIdAndEmail(userId, email);
	    if (user == null) return false;

	    // 4. 임시 비번 생성 및 암호화
	    String tempPassword = UUID.randomUUID().toString().substring(0, 10);
	    String hashed = encoder.encode(tempPassword);
	    userMapperOC.updatePassword(userId, hashed);

	    // 5. 로그 저장
	    userMapperOC.insertPwResetLog(userId, email);

	    // 6. 메일 발송
	    mailService.sendTempPasswordEmail(email, tempPassword);
	    return true;
	}

}
