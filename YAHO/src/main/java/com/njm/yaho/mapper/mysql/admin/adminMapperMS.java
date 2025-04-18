package com.njm.yaho.mapper.mysql.admin;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.njm.yaho.domain.mysql.admin.AnimeMSDTO;

@Mapper
public interface adminMapperMS {
    void insertAnime(AnimeMSDTO anime);
        
    List<AnimeMSDTO> getAllAnimes(); // 제목 목록 불러오기

    void updateAnime(AnimeMSDTO anime);
}
