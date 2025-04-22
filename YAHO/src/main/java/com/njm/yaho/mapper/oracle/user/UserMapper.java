package com.njm.yaho.mapper.oracle.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
	int countByEmail(@Param("email") String email); // 이메일 중복 체크
}
