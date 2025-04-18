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
    	ms.insertAnime(anime);
    }
    
    @Override
    public void insertAnimeOC(AnimeOCDTO anime) {
    	oc.insertAnime(anime);
    }
    
    @Override
    public List<AnimeMSDTO> getAllAnimes() {
    	return ms.getAllAnimes();
    }

    @Override
    public void updateAnimeAndInfo(int animeId, String title, MultipartFile garoImage, MultipartFile seroImage,
                                   List<String> weekdays, double score, String tags,
                                   String animeDesc, String startDate, int totalEpisode, int currentEpisode,
                                   double animeScore, String ageRating) {

        // DB에 넘길 DTO 만들기
        AnimeMSDTO anime = new AnimeMSDTO();
        anime.setANIME_ID(animeId);
        anime.setTITLE(title);
        anime.setSCORE(score);
        anime.setTAGS(tags);
        anime.setWEEKDAY(String.join(",", weekdays));

        if (garoImage != null && !garoImage.isEmpty()) {
            anime.setTHUMNAIL_GARO_URL(garoImage.getOriginalFilename());
            // 파일 저장 로직 추가하면 됨
        }
        if (seroImage != null && !seroImage.isEmpty()) {
            anime.setTHUMNAIL_SERO_URL(seroImage.getOriginalFilename());
        }

        AnimeOCDTO animeInfo = new AnimeOCDTO();
        animeInfo.setANIME_ID(animeId);
        animeInfo.setANIME_DESC(animeDesc);
        animeInfo.setSTART_DATE(startDate);
        animeInfo.setTOTAL_EPISODE(totalEpisode);
        animeInfo.setCURRENT_EPISODE(currentEpisode);
        animeInfo.setANIME_SCORE(animeScore);
        animeInfo.setAGE_RATING(ageRating);

        // Mapper 호출
        ms.updateAnime(anime);
        oc.updateAnimeInfo(animeInfo);
    }
}
