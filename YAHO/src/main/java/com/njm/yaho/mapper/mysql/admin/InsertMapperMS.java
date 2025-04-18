package com.njm.yaho.mapper.mysql.admin;

import com.njm.yaho.domain.mysql.admin.InsertMSDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InsertMapperMS {
    void insertAnime(InsertMSDTO anime);
    
    List<InsertMSDTO> findAll();  // 전체 목록 가져오기
}
