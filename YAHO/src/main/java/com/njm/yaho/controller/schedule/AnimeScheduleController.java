package com.njm.yaho.controller.schedule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njm.yaho.controller.info.RateControllerTest;
import com.njm.yaho.domain.oracle.info.RatingChartDTO;
import com.njm.yaho.domain.oracle.info.RatingDTO;
import com.njm.yaho.service.info.RateService;
import com.njm.yaho.service.schedule.ScheduleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Schedule")
public class AnimeScheduleController {
	private static final Logger log = LoggerFactory.getLogger(RateControllerTest.class);

	@Autowired
	private ScheduleService service;
	
	@Autowired
	private RateService Rateservice;

	@GetMapping("animeSchedule")
	public void showAnimeList(Model model, HttpSession session,int ANIME_ID) {
		
		String USER_ID = (String)session.getAttribute("USER_ID");
		//유저 아이디 보내기
		model.addAttribute("USER_ID",USER_ID);
		log.info("세션 유저아이디"+USER_ID);
		
		// 요일 리스트 (요일 순서 보장)
		List<String> daysOfWeek = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");

		// 모델에 데이터 추가
		model.addAttribute("daysOfWeek", daysOfWeek);
		model.addAttribute("animeByDay", service.WeekdayAnimeList());

		// 모델에 평균
		
		double grade = Rateservice.getAverageScore(ANIME_ID);
		log.info("평균: " + grade);

		model.addAttribute("grade", grade);

		String mark = "";

		if (grade >= 1.0 && grade < 2.0) {
			mark = "별로에요";
		} else if (grade < 3.0) {
			mark = "평범해요";
		} else if (grade < 4.0) {
			mark = "훌륭해요";
		} else {
			mark = "명작";
		}

		model.addAttribute("gradeMark", mark);

		// 막대그래프

		List<RatingDTO> ratingList = Rateservice.selectRateCount(ANIME_ID);

		// 5개의 구간 정의: 0~1, 1~2, 2~3, 3~4, 4~5
		List<String> scoreLabels = Arrays.asList("0~1", "1~2", "2~3", "3~4", "4~5");
		int[] countPerRange = new int[5];

		for (RatingDTO dto : ratingList) {
			double score = dto.getSCORE_SCORE();
			int count = dto.getCOUNT();

			if (score >= 0 && score < 1) {
				countPerRange[0] += count;
			} else if (score >= 1 && score < 2) {
				countPerRange[1] += count;
			} else if (score >= 2 && score < 3) {
				countPerRange[2] += count;
			} else if (score >= 3 && score < 4) {
				countPerRange[3] += count;
			} else if (score >= 4 && score <= 5) {
				countPerRange[4] += count;
			}
		}

		// 리스트로 변환하여 모델에 전달
		List<Integer> countList = Arrays.stream(countPerRange).boxed().collect(Collectors.toList());

		model.addAttribute("scoreList", scoreLabels); // X축 레이블
		model.addAttribute("countList", countList); // Y축 값

		log.info("X축 구간: " + scoreLabels);
		log.info("Y축 count: " + countList);

		// 도넛 통계
		List<RatingChartDTO> RClist = Rateservice.selectGenderCount(ANIME_ID);
		log.info("도넛 차트 리스트: " + RClist);

		if (RClist != null && !RClist.isEmpty()) {
			RatingChartDTO dto = RClist.get(0);
			int menCount = dto.getMENCOUNT();
			int girlCount = dto.getGIRLCOUNT();

			int total = menCount + girlCount;
			double maleRatio = total > 0 ? Math.round((menCount * 100.0 / total) * 10.0) / 10.0 : 0.0;
			double femaleRatio = total > 0 ? Math.round((girlCount * 100.0 / total) * 10.0) / 10.0 : 0.0;

			model.addAttribute("maleRatio", maleRatio);
			model.addAttribute("femaleRatio", femaleRatio);

			log.info("남자: " + maleRatio);
			log.info("여자: " + femaleRatio);
		} else {
			log.info("RClist가 비어있다");
			model.addAttribute("maleRatio", 0);
			model.addAttribute("femaleRatio", 0);

			// System.out.println("애니 요일별 리스트: " + service.WeekdayAnimeList());

			// 게시판

		}

		// 평점 리스트 가져오기
		
		List<RatingDTO> fullList = Rateservice.selectRateListByAnime(ANIME_ID);
		log.info("전체 평점 리스트:"+fullList);
		RatingDTO matchedDto = null;
		log.info("USER_ID:"+USER_ID);
		// USER_ID가 유효할 때만 필터링
		if (USER_ID != null && !USER_ID.trim().isEmpty()) {
			String trimmedId = USER_ID.trim();
			
			// 일치하는 DTO 찾기
			for (RatingDTO dto : fullList) {
				if (trimmedId.equals(dto.getUSER_ID())) {
					matchedDto = dto;
					model.addAttribute("Aluser_ID", trimmedId);
					model.addAttribute("Aldto", dto);
					log.info("특정 유저아이디 dto 확인: " + trimmedId + " / " + dto);
					break;
				}
			}

			// 리스트에서 일치하는 DTO 제거
			if (matchedDto != null) {
				fullList.remove(matchedDto);
			}
		}

		model.addAttribute("list", fullList);
	}
	@PostMapping("/submitTest")
	public String submitTest(RatingDTO dto, Model model) {
		log.info("dto.SCORE_ID : " + dto.getSCORE_ID());
		log.info("dto.ANIME_ID : " + dto.getANIME_ID());
		log.info("dto.SCORE_CONTENT : " + dto.getSCORE_CONTENT());
		log.info("dto.SCORE_SCORE : " + dto.getSCORE_SCORE());
		log.info("dto.USER_ID : " + dto.getUSER_ID());

		int row = Rateservice.insertRate(dto);
		log.info("데이터 등록" + row);
		return "redirect:/Schedule/animeSchedule?ANIME_ID=999";
	}

	@PostMapping("/updateRatePro")
	public String updateRatePro(Model model, RatingDTO dto) {
		int row = Rateservice.updateRate(dto);
		log.info("row:" + row);
		log.info("컨텐츠:" + dto.getSCORE_CONTENT());
		model.addAttribute("dto", dto);
		return "redirect:/Schedule/animeSchedule";
	}
	@PostMapping("/deleteRate")
	public String deleteRate(Model model, int ANIME_ID,String USER_ID) {
		int row = Rateservice.deleteRate(ANIME_ID,USER_ID);
		log.info("삭제 row: "+row);
		return"redirect:/Schedule/animeSchedule";
	}
	
	//Ajax 평점 등록
	@PostMapping("/ajaxInsert")
	@ResponseBody
	public Map<String, Object> ajaxInsert(@RequestBody RatingDTO dto) {
	    log.info("▶ [AJAX 등록] USER_ID: " + dto.getUSER_ID());
	    log.info("▶ [AJAX 등록] ANIME_ID: " + dto.getANIME_ID());
	    log.info("▶ [AJAX 등록] SCORE: " + dto.getSCORE_SCORE());
	    log.info("▶ [AJAX 등록] CONTENT: " + dto.getSCORE_CONTENT());
	    
	    log.info("▶ JSON 전체 출력: " + dto.toString()); // DTO에 toString 오버라이딩 되어 있다면 사용
	    
	    
	    int row = Rateservice.insertRate(dto);

	    Map<String, Object> result = new HashMap<>();
	    result.put("success", row > 0);
	    result.put("message", row > 0 ? "등록 완료!" : "등록 실패");

	    return result;
	}
	
	//Ajax 평점 게시판 
	@PostMapping("/refreshRate")
	@ResponseBody
	public Map<String, Object> refreshRate(@RequestBody RatingDTO dto) {
	    Map<String, Object> map = new HashMap<>();

	    int animeId = dto.getANIME_ID();
	    String userId = dto.getUSER_ID();

	    // ✅ 평균 평점 및 텍스트
	    double grade = Rateservice.getAverageScore(animeId);
	    String mark = (grade >= 4.0) ? "명작" :
	                  (grade >= 3.0) ? "훌륭해요" :
	                  (grade >= 2.0) ? "평범해요" : "별로에요";

	    map.put("grade", grade);
	    map.put("mark", mark);

	    // ✅ 성별 도넛 통계
	    List<RatingChartDTO> chartList = Rateservice.selectGenderCount(animeId);
	    double maleRatio = 0, femaleRatio = 0;
	    if (chartList != null && !chartList.isEmpty()) {
	        RatingChartDTO genderDto = chartList.get(0);
	        int men = genderDto.getMENCOUNT();
	        int women = genderDto.getGIRLCOUNT();
	        int total = men + women;

	        maleRatio = total > 0 ? Math.round((men * 100.0 / total) * 10) / 10.0 : 0.0;
	        femaleRatio = total > 0 ? Math.round((women * 100.0 / total) * 10) / 10.0 : 0.0;
	    }
	    map.put("maleRatio", maleRatio);
	    map.put("femaleRatio", femaleRatio);

	    // ✅ 댓글 목록 (작성자 제외)
	    List<RatingDTO> allList = Rateservice.selectRateListByAnime(animeId);
	    List<RatingDTO> filteredList = allList.stream()
	            .filter(r -> !r.getUSER_ID().equals(userId))
	            .collect(Collectors.toList());
	    map.put("list", filteredList);

	    // ✅ 막대 차트 데이터: scoreList + countList
	    List<String> scoreLabels = Arrays.asList("0~1", "1~2", "2~3", "3~4", "4~5");
	    int[] countPerRange = new int[5];

	    List<RatingDTO> rangeList = Rateservice.selectRateCount(animeId);
	    for (RatingDTO r : rangeList) {
	        double score = r.getSCORE_SCORE();
	        int count = r.getCOUNT();

	        if (score >= 0 && score < 1) countPerRange[0] += count;
	        else if (score < 2) countPerRange[1] += count;
	        else if (score < 3) countPerRange[2] += count;
	        else if (score < 4) countPerRange[3] += count;
	        else if (score <= 5) countPerRange[4] += count;
	    }

	    List<Integer> countList = Arrays.stream(countPerRange).boxed().collect(Collectors.toList());

	    map.put("scoreList", scoreLabels);
	    map.put("countList", countList);

	    return map;
	}
}
