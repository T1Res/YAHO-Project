package com.njm.yaho.service.admin;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.njm.yaho.domain.mysql.admin.AnimeMSDTO;
import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;

public interface AnimeService {
    void insertAnimeMS(AnimeMSDTO anime);
    
    void insertAnimeOC(AnimeOCDTO anime);
    
    List<AnimeMSDTO> getAllAnimes(); // 제목 목록 불러오기

    void updateAnimeAndInfo(int animeId, String title, MultipartFile garoImage, MultipartFile seroImage,
            List<String> weekdays, double score, String tags,
            String animeDesc, String startDate, int totalEpisode, int currentEpisode,
            double animeScore, String ageRating);
}
