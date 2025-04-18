package com.njm.yaho.controller.admin;

import com.njm.yaho.domain.mysql.admin.InsertMSDTO;
import com.njm.yaho.service.admin.InsertService;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Admin")
public class AdminAnimeController {

    @Autowired
    private InsertService animeService;

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
        InsertMSDTO anime = new InsertMSDTO();
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
        animeService.insertAnime(anime);
        return "redirect:/Admin/insert";
    }
}
