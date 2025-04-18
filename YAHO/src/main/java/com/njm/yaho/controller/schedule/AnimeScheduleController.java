package com.njm.yaho.controller.schedule;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njm.yaho.service.schedule.ScheduleService;

@Controller
@RequestMapping("/Schedule")
public class AnimeScheduleController {
	@Autowired
	private ScheduleService service;
	
	@GetMapping("animeSchedule")
	public void showAnimeList(Model model) {
        // 요일 리스트 (요일 순서 보장)
        List<String> daysOfWeek = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");

        // 모델에 데이터 추가
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("animeByDay", service.WeekdayAnimeList());
        
        //System.out.println("애니 요일별 리스트: " + service.WeekdayAnimeList());
	}
}
