package com.njm.yaho.service.admin;

import java.util.List;

import com.njm.yaho.domain.mysql.admin.AnimeMSDTO;
import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;

public interface AnimeService {
    void insertAnimeMS(AnimeMSDTO anime);
    
    void insertAnimeOC(AnimeOCDTO anime);
    
	void insertFullAnime(AnimeMSDTO animeMS, AnimeOCDTO animeOC);

	List<AnimeMSDTO> getmslist(); // 제목 목록 불러오기
	
	List<AnimeOCDTO> getoclist(); // 제목 목록 불러오기
	

	void deleteMS(int animeId);
	void deleteOC(int animeId);
	
}
