package com.njm.yaho.service.user;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.njm.yaho.domain.oracle.user.UserDTO;
import com.njm.yaho.mapper.oracle.user.UserMapper;
import com.njm.yaho.util.PassUtil;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper mapper;
	
	@Override
	public int UserInsert(UserDTO dto) {
		// TODO Auto-generated method stub
		return mapper.UserInsert(dto);
	}
	
	@Override
	public UserDTO UserLogin(String USER_ID, String USER_PASSWORD) {
		UserDTO user = mapper.UserSearch(USER_ID); // DB에서 회원 조회

	    if (user != null && PassUtil.checkPassword(USER_PASSWORD, user.getUSER_PASSWORD())) {
	        return user; // 비밀번호 일치 시 로그인 성공
	    }

	    return null; // 실패
	}

	@Override
	public UserDTO UserIdCheck(String USER_ID) {
		
		return mapper.UserIdCheck(USER_ID);
	}
	@Override
	public UserDTO UserSearch(String USER_ID) {
		// TODO Auto-generated method stub
		return mapper.UserSearch(USER_ID);
	}
	@Override
	public UserDTO UserEmailCheck(String USER_EMAIL) {
		// TODO Auto-generated method stub
		return mapper.UserEmailCheck(USER_EMAIL);
	}
	@Override
	public void UserModify(UserDTO dto) {
		// TODO Auto-generated method stub
		mapper.UserModify(dto);
	}
	@Override
	public void UserDelete(String USER_ID) {
		// TODO Auto-generated method stub
		mapper.UserDelete(USER_ID);
	}
	@Override
	public UserDTO UserProfile(String USER_ID) {
		// TODO Auto-generated method stub
		
		
		return mapper.UserProfile(USER_ID);
	}
	@Value("${profile.upload.path}") // application.properties에 설정
	    private String uploadDir;
	@Override
	public String saveProfileImage(MultipartFile file) {
		 if (file == null || file.isEmpty()) {
	            return "/img/default_profile.png"; // 기본 이미지 경로
	        }

	        // 고유 파일명 생성
	        String originalFilename = file.getOriginalFilename();
	        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	        String newFilename = UUID.randomUUID().toString() + extension;

	        // 저장 디렉토리 경로
	        File directory = new File(uploadDir);
	        if (!directory.exists())directory.mkdirs();
	        
	        // 실제 저장
	        File destination = new File(directory, newFilename);
	        try {
	            file.transferTo(destination);
	        } catch (IOException e) {
	            throw new RuntimeException("파일 저장 중 오류 발생", e);
	        }

	        // 웹에서 접근 가능한 경로 반환
	        return "/upload/profile/" + newFilename;
	}
}
