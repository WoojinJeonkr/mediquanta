package com.application.mediquanta.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.community.dto.CommunityDTO;
import com.application.mediquanta.community.service.CommunityService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;

	@GetMapping
	public String viewRounge(HttpSession session, Model model) {
		String role = (String)session.getAttribute("role");
		if (role != null) model.addAttribute("role", role);
		model.addAttribute("communityList", communityService.getCommunityList());
		return "community/rounge";
	}
	
	@GetMapping("/createCommunity")
	public String createCommunity() {
		return "community/communityCreate";
	}
	
	@PostMapping("/validCommunityName")
	@ResponseBody
	public String validCommunityName(@RequestParam("communityName") String communityName) {
		return communityService.validCommunityName(communityName);
	}
	
	@PostMapping("/createCommunity")
	public String createCommunity(@ModelAttribute CommunityDTO communityDTO) {
		communityService.createCommunity(communityDTO);
		return "community/rounge";
	}
	
	@PostMapping("/agreeCommunity")
	@ResponseBody
	public void agreeCommunity(@RequestParam("communityId") long communityId) {
		communityService.agreeCommunity(communityId);
	}
	
	
}
