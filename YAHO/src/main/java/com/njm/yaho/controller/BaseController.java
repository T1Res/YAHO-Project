package com.njm.yaho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
	@GetMapping("/")
	public String redirectToMain() {
	    return "redirect:/Main";
	}
}
