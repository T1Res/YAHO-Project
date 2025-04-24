package com.njm.yaho.controller.info;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Info")
public class InfoController {

	@GetMapping("/teamInfo")
	public void teamInfo() {
	}
}
