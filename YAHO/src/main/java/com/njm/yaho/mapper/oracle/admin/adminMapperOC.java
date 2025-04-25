package com.njm.yaho.mapper.oracle.admin;

import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface adminMapperOC {
//    void insertInfo(AnimeOCDTO anime); 
	
	void insertAnimeOC(AnimeOCDTO anime);
    
	List<AnimeOCDTO> getoclist(); // 제목 목록 불러오기
	
	
	
	
	
	
	
	 // 애니메이션 상세 정보 수정
	    int updateAnimeDetail(AnimeOCDTO animeDetailDTO);

	    // 애니메이션 상세 정보 조회
	    AnimeOCDTO getAnimeDetailById(int animeId);
	
	
	
	
	
	 void deleteAnime(int animeId);
}
