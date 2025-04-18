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

    // 등록 폼 보여주는 GET 요청
    @GetMapping("/insert")
    public String showInsertForm() {
        return "Admin/admin_insert.html"; // templates/Admin/admin_insert.html
    }

    // 등록 처리하는 POST 요청
    @PostMapping("/insert")
    public String insertAnime(
        @RequestParam String TITLE,
        @RequestParam(required = false) MultipartFile THUMNAIL_GARO_URL,
        @RequestParam(required = false) MultipartFile THUMNAIL_SERO_URL,
        @RequestParam(required = false) String[] WEEKDAY,
        @RequestParam double SCORE,
        @RequestParam String TAGS
    ) {
        AnimeMSDTO anime = new AnimeMSDTO();
        anime.setTITLE(TITLE);

        // static/IMG 경로로 저장
        String uploadGARO = System.getProperty("user.dir") + "/src/main/resources/static/IMG/ANIME/GARO/";
        String uploadSERO = System.getProperty("user.dir") + "/src/main/resources/static/IMG/ANIME/SERO/";
        
        // 폴더 없으면 생성
        File folder = new File(uploadGARO);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        File folder2 = new File(uploadSERO);
        if (!folder2.exists()) {
            folder2.mkdirs();
        }

        try {
            if (THUMNAIL_GARO_URL != null && !THUMNAIL_GARO_URL.isEmpty()) {
                File saveFile = new File(uploadGARO + THUMNAIL_GARO_URL.getOriginalFilename());
                THUMNAIL_GARO_URL.transferTo(saveFile);
                anime.setTHUMNAIL_GARO_URL("/IMG/ANIME/GARO/" + THUMNAIL_GARO_URL.getOriginalFilename()); // DB에는 파일명 저장
            }

            if (THUMNAIL_SERO_URL != null && !THUMNAIL_SERO_URL.isEmpty()) {
                File saveFile = new File(uploadSERO + THUMNAIL_SERO_URL.getOriginalFilename());
                THUMNAIL_SERO_URL.transferTo(saveFile);
                anime.setTHUMNAIL_SERO_URL("/IMG/ANIME/SERO/" + THUMNAIL_SERO_URL.getOriginalFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // 에러 페이지나 메시지 페이지
        }

        // 요일 배열 → 문자열
        if (WEEKDAY != null) {
            anime.setWEEKDAY(String.join(",", WEEKDAY));
        } else {
            anime.setWEEKDAY("");
        }

        anime.setSCORE(SCORE);
        anime.setTAGS(TAGS);

        // DB 저장
        animeService.insertAnimeMS(anime);
        return "redirect:/Admin/list";
    }
    
    @GetMapping("/insert_info")
    public String showInfoForm() {
        return "Admin/admin_info"; // 기본 폼
    }

   

    // 등록 처리하는 POST 요청
    @PostMapping("/insert_info")
    public String insertInfo(
        @RequestParam int ANIME_ID,
        @RequestParam String ANIME_DESC,
        @RequestParam String START_DATE,
        @RequestParam int TOTAL_EPISODE,
        @RequestParam int CURRENT_EPISODE,
        @RequestParam double ANIME_SCORE,
        @RequestParam String AGE_RATING
    ) {
        AnimeOCDTO anime = new AnimeOCDTO();
        anime.setANIME_ID(ANIME_ID);
        anime.setANIME_DESC(ANIME_DESC);
        anime.setSTART_DATE(START_DATE);
        anime.setTOTAL_EPISODE(TOTAL_EPISODE);
        anime.setCURRENT_EPISODE(CURRENT_EPISODE);
        anime.setANIME_SCORE(ANIME_SCORE);
        anime.setAGE_RATING(AGE_RATING);

        // DB 저장
        animeService.insertAnimeOC(anime);
        return "redirect:/Admin/list";
    }
    
    @GetMapping("/list")
    public String animeList(Model model) {
        List<AnimeMSDTO> animeList = animeService.getAllAnimes();
        model.addAttribute("animeList", animeList);
        return "Admin/anime_list.html"; // templates/animeList.html
    }
    
    

}

