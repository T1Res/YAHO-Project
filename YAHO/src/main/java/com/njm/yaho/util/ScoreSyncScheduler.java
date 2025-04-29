package com.njm.yaho.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.njm.yaho.service.admin.AnimeService;

@Component
public class ScoreSyncScheduler {
	@Autowired
	private AnimeService animeService;
	
	@Scheduled(fixedRate = 600000) // 매일 자정에 실행
	public void syncScores() {
		System.out.println("[SCHEDULER] 애니 평점 자동 동기화 시작");
		
		animeService.syncScoreManually();
		
		System.out.println("[SCHEDULER] 동기화 완료");
	}
}
