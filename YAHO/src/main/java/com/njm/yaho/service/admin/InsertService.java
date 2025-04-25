package com.njm.yaho.service.admin;

import java.util.List;

import com.njm.yaho.domain.mysql.admin.InsertMSDTO;

public interface InsertService {
    void insertAnime(InsertMSDTO anime);
    
    List<InsertMSDTO> findAll();
    
    public void syncScoreManually();
}
