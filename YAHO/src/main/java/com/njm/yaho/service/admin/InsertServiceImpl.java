package com.njm.yaho.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njm.yaho.domain.mysql.admin.InsertMSDTO;
import com.njm.yaho.mapper.mysql.admin.InsertMapperMS;

@Service
public class InsertServiceImpl implements InsertService {
    // MySQL Mapper
    @Autowired
    private InsertMapperMS mapperMS;

    @Override
    public void insertAnime(InsertMSDTO anime) {
    	mapperMS.insertAnime(anime);
    }
    
    @Override
    public List<InsertMSDTO> findAll() {
    	return mapperMS.findAll();
    }
}
