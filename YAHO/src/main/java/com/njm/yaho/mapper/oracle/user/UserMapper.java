package com.njm.yaho.mapper.oracle.user;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.web.multipart.MultipartFile;

import com.njm.yaho.domain.oracle.user.UserDTO;

@Mapper
public interface UserMapper {
	// 로그인
	public UserDTO UserLogin(String USER_ID, String USER_PASSWORD);
	// 회원가입
	public int UserInsert(UserDTO dto);
	// 중복검사
	public UserDTO UserIdCheck(String USER_ID);
	//유저검색
	public UserDTO UserSearch(String USER_ID);
	//email중복
	public UserDTO UserEmailCheck(String USER_EMAIL);
	// 회원수정
	public void UserModify(UserDTO dto);
	// 회원탈퇴
	public void UserDelete(String USER_ID);
	// 프로필보기
	public UserDTO UserProfile(String USER_ID);
	public String saveProfileImage(MultipartFile file);
}
