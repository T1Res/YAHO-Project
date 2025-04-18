package com.njm.yaho.service.main;

import java.util.List;

import com.njm.yaho.domain.mysql.main.MainMSDTO;
import com.njm.yaho.domain.oracle.main.MainOCDTO;

public interface MainService {
	// 오늘 방영중 애니 목록 가져오기
	public List<MainMSDTO> getTodayAnimeList();
	
	// 특정 애니 정보 가져오기
	public MainOCDTO getAnimeInfo(int animeId);
}
