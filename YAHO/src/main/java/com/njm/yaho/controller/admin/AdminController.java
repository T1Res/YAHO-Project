package com.njm.yaho.controller.admin;

import com.njm.yaho.domain.mysql.admin.AnimeMSDTO;
import com.njm.yaho.domain.oracle.admin.AnimeOCDTO;
import com.njm.yaho.service.admin.AnimeService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AnimeService animeService;

  
    @PostMapping("/insert_full")
    public String insertFullAnime(
        @RequestParam String TITLE,
        @RequestParam(required = false) MultipartFile THUMNAIL_GARO_URL,
        @RequestParam(required = false) MultipartFile THUMNAIL_SERO_URL,
        @RequestParam(required = false) String[] WEEKDAY,
        @RequestParam double SCORE,
        @RequestParam String TAGS,

        // Oracle용
        @RequestParam String ANIME_DESC,
        @RequestParam String START_DATE,
        @RequestParam int TOTAL_EPISODE,
        @RequestParam int CURRENT_EPISODE,
        @RequestParam double ANIME_SCORE,
        @RequestParam String AGE_RATING
    ) {
        AnimeMSDTO animeMS = new AnimeMSDTO();
        animeMS.setTITLE(TITLE);

        String uploadGARO = System.getProperty("user.dir") + "/src/main/resources/static/IMG/ANIME/GARO/";
        String uploadSERO = System.getProperty("user.dir") + "/src/main/resources/static/IMG/ANIME/SERO/";

        try {
            if (THUMNAIL_GARO_URL != null && !THUMNAIL_GARO_URL.isEmpty()) {
                File saveFile = new File(uploadGARO + THUMNAIL_GARO_URL.getOriginalFilename());
                THUMNAIL_GARO_URL.transferTo(saveFile);
                animeMS.setTHUMNAIL_GARO_URL("/IMG/ANIME/GARO/" + THUMNAIL_GARO_URL.getOriginalFilename());
            }

            if (THUMNAIL_SERO_URL != null && !THUMNAIL_SERO_URL.isEmpty()) {
                File saveFile = new File(uploadSERO + THUMNAIL_SERO_URL.getOriginalFilename());
                THUMNAIL_SERO_URL.transferTo(saveFile);
                animeMS.setTHUMNAIL_SERO_URL("/IMG/ANIME/SERO/" + THUMNAIL_SERO_URL.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        if (WEEKDAY != null) {
            animeMS.setWEEKDAY(String.join(",", WEEKDAY));
        } else {
            animeMS.setWEEKDAY("");
        }

        animeMS.setSCORE(SCORE);
        animeMS.setTAGS(TAGS);

        // MySQL 저장 → 생성된 ID 받아오기
        animeService.insertAnimeMS(animeMS); // ID가 자동으로 설정된다면
        int generatedAnimeId = animeMS.getANIME_ID(); // insert 이후 ID 리턴되게 설정 필요

        // Oracle 저장
        AnimeOCDTO animeOC = new AnimeOCDTO();
        animeOC.setANIME_ID(generatedAnimeId); // 위에서 받은 ID 사용
        animeOC.setANIME_DESC(ANIME_DESC);
        animeOC.setSTART_DATE(START_DATE);
        animeOC.setTOTAL_EPISODE(TOTAL_EPISODE);
        animeOC.setCURRENT_EPISODE(CURRENT_EPISODE);
        animeOC.setANIME_SCORE(ANIME_SCORE);
        animeOC.setAGE_RATING(AGE_RATING);

        animeService.insertAnimeOC(animeOC);

        return "redirect:/Admin/list";
    }

    
    
//    수정 위한 애니 제목 리스트 출력 위한 어쩌고저쩌고..
    @GetMapping("/list")
    public String animeList(Model model) {
        List<AnimeMSDTO> mslist = animeService.getmslist();
        List<AnimeOCDTO> oclist = animeService.getoclist();
        System.out.println("OC List: " + oclist);
        model.addAttribute("mslist", mslist);
        model.addAttribute("oclist", oclist);
        return "Admin/anime_list.html"; // templates/animeList.html
    }
    
    
   
}

