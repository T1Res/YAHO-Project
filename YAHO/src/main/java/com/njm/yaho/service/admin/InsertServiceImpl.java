package com.njm.yaho.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.njm.yaho.domain.mysql.admin.InsertMSDTO;
import com.njm.yaho.mapper.mysql.admin.InsertMapperMS;
import com.njm.yaho.mapper.mysql.admin.ScoreMapperMS;
import com.njm.yaho.mapper.oracle.admin.ScoreMapperOC;

@Service
public class InsertServiceImpl implements InsertService {
    // MySQL Mapper
    @Autowired
    private InsertMapperMS mapperMS;
    
    @Autowired
    private ScoreMapperOC scoreMapperOC;
    
    @Autowired
    private ScoreMapperMS scoreMapperMS;

    @Override
    public void insertAnime(InsertMSDTO anime) {
    	mapperMS.insertAnime(anime);
    }
    
    @Override
    public List<InsertMSDTO> findAll() {
    	return mapperMS.findAll();
    }
    
    @Override
    public void syncScoreManually() {
        List<Integer> animeIds = scoreMapperOC.getAllAnimeIds(); // TBL_SCORE에서 ANIME_ID 목록 가져오기

        for (Integer animeId : animeIds) {
            Double avg = scoreMapperOC.getAverageScoreByAnimeId(animeId); // 평균 구하기
            if (avg == null) avg = 0.0;

            scoreMapperOC.updateAnimeScore(animeId, avg); // Oracle 업데이트
            scoreMapperMS.updateMySQLScore(animeId, avg);       // MySQL 동기화
        }
    }
}
