package com.njm.yaho.service.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.njm.yaho.domain.mysql.admin.AnimeMSDTO;
import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;
import com.njm.yaho.mapper.mysql.admin.adminMapperMS;
import com.njm.yaho.mapper.oracle.admin.adminMapperOC;

@Service
public class AnimeServiceImpl implements AnimeService {
    // MySQL Mapper
    @Autowired
    private adminMapperMS ms;
    
    @Autowired
	private adminMapperOC oc;

    
    @Override
    public void insertAnimeMS(AnimeMSDTO anime) {
    	ms.insertAnimeMS(anime);
    }
    
    @Override
    public void insertAnimeOC(AnimeOCDTO anime) {
    	oc.insertAnimeOC(anime);
    }
    
    
    @Override
    public void insertFullAnime(AnimeMSDTO animeMS, AnimeOCDTO animeOC) {
        ms.insertAnimeMS(animeMS);
        animeOC.setANIME_ID(animeMS.getANIME_ID());
        oc.insertAnimeOC(animeOC);
    }

    
    @Override
    public void updateAnimeBasic(AnimeMSDTO anime) {
        ms.updateAnimeBasic(anime);
    }
    
    @Override
    public void updateAnimeDetail(AnimeOCDTO anime) {
        oc.updateAnimeDetail(anime);
    }
    
    
    @Override
    public List<AnimeMSDTO> getmslist() {
    	return ms.getmslist();
    }
    
    @Override
    public List<AnimeOCDTO> getoclist() {
    	return oc.getoclist();
    }

    
    @Override
    public void deleteMS(int animeId) {
        ms.deleteAnime(animeId);
    }
    
    @Override
    public void deleteOC(int animeId) {
        oc.deleteAnime(animeId);
    }
}
