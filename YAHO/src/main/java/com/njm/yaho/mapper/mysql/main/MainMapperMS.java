package com.njm.yaho.mapper.mysql.main;

import java.util.List;

import com.njm.yaho.domain.mysql.main.MainMSDTO;

public interface MainMapperMS {
	// 오늘 방영중 애니 목록 가져오기
	public List<MainMSDTO> getTodayAnimeList(String today);
}
