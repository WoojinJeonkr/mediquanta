package com.application.mediquanta.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@GetMapping
	public String viewRounge(HttpSession session) {
		return "community/rounge";
	}
	
}
