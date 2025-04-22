package com.njm.yaho.domain.oracle.util;

import java.time.LocalDateTime;

@lombok.Data
public class EmailAuthDTO {
	private String email;
    private String authCode;
    private String isVerified;
    private LocalDateTime expireTime;
    private LocalDateTime sentTime;
    private int tryCount;
}
