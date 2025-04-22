package com.njm.yaho.mapper.oracle.util;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.njm.yaho.domain.oracle.util.EmailAuthDTO;

@Mapper
public interface EmailAuthMapper {
	EmailAuthDTO findByEmail(@Param("email") String email);

    void insertEmailAuth(EmailAuthDTO dto);

    void updateEmailAuth(EmailAuthDTO dto);

    void deleteByEmail(@Param("email") String email);
    
    EmailAuthDTO findByEmailAndCode(@Param("email") String email, @Param("authCode") String authCode);

    void markEmailAsVerified(@Param("email") String email);
    
    void updateAuthCodeAndIncrementTryCount(@Param("email") String email,
            								@Param("authCode") String authCode,
            								@Param("expireTime") LocalDateTime expireTime,
            								@Param("sentTime") LocalDateTime sentTime);

}
