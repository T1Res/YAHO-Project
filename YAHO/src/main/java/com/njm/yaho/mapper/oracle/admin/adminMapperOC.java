package com.njm.yaho.mapper.oracle.admin;

import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface adminMapperOC {
    void insertAnime(AnimeOCDTO anime);
    
    void insertInfo(AnimeOCDTO anime); 
    
    void updateAnimeInfo(AnimeOCDTO animeInfo);
}
