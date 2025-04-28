package com.njm.yaho.controller.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njm.yaho.controller.info.RateControllerTest;
import com.njm.yaho.domain.mysql.main.MainMSDTO;
import com.njm.yaho.domain.oracle.main.MainOCDTO;
import com.njm.yaho.service.main.MainService;
import com.njm.yaho.service.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller

@RequestMapping("/Main")
public class MainController {
	@Autowired
	private MainService service;
	private static final Logger log = LoggerFactory.getLogger(RateControllerTest.class);
  
	@GetMapping("")
	public String main(Model model,@ModelAttribute("playAudio") Object flashAudio,HttpSession session) {
		List<MainMSDTO> animeList = service.getTodayAnimeList();
		int animeCount = animeList.size();
		log.info("세션 로그인 확인"+(String)session.getAttribute("USER_ID"));
		model.addAttribute("animeCount", animeCount);
		model.addAttribute("animeList", animeList);
		if (flashAudio != null) {
	        model.addAttribute("playAudio", true);
	    }
		
		String USER_ID = (String)session.getAttribute("USER_ID");
		model.addAttribute("USER_ID", USER_ID);
        return "Main/index";
    }
	
	// 요약정보 전송
	@GetMapping("/anime/baseInfo")
	@ResponseBody
	public MainMSDTO getAnimeBaseInfo( int animeId) {
		MainMSDTO dto = service.getAnimeBaseInfo(animeId);
		log.info("dto, animeId"+dto+animeId);
        return dto;
	}
	
	// 상세정보 전송
	@GetMapping("/anime/detailInfo")
	@ResponseBody
	public MainOCDTO getAnimeDetailInfo(int animeId) {
        MainOCDTO dto = service.getAnimeDetailInfo(animeId);
        return dto;
	}
}
