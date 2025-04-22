package com.njm.yaho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // 비동기 기능 활성화
public class YahoApplication {

	public static void main(String[] args) {
		SpringApplication.run(YahoApplication.class, args);
	}

}
