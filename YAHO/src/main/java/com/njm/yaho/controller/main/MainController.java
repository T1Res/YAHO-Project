package com.njm.yaho.controller.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njm.yaho.domain.mysql.main.MainMSDTO;
import com.njm.yaho.domain.oracle.main.MainOCDTO;
import com.njm.yaho.service.main.MainService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Main")
public class MainController {
	@Autowired
	private MainService service;
	
	@GetMapping("")
	public String main(Model model,HttpSession session) {
		List<MainMSDTO> animeList = service.getTodayAnimeList();
		int animeCount = animeList.size();
		
		model.addAttribute("animeCount", animeCount);
		model.addAttribute("animeList", animeList);
		
		Object playAudio = session.getAttribute("playAudio");
	    if (playAudio != null && (boolean) playAudio) {
	        model.addAttribute("playAudio", true);
	        session.removeAttribute("playAudio"); // ✅ 재생 후 삭제
	    }
        return "Main/index";
    }
	
	// 요약정보 전송
	@GetMapping("/anime/baseInfo")
	@ResponseBody
	public MainMSDTO getAnimeBaseInfo(int animeId) {
		MainMSDTO dto = service.getAnimeBaseInfo(animeId);
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
