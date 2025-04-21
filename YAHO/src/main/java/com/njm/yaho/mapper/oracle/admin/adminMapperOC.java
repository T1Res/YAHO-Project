package com.njm.yaho.mapper.oracle.admin;

import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface adminMapperOC {
//    void insertInfo(AnimeOCDTO anime); 
	
	void insertAnimeOC(AnimeOCDTO anime);
    
	List<AnimeOCDTO> getoclist(); // 제목 목록 불러오기
}
