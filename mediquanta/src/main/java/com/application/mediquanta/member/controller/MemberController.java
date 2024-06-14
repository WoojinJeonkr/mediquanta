package com.application.mediquanta.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mediquanta/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
	
	@PostMapping("/validNickname")
	@ResponseBody
	public String validNickname(@RequestParam("nickname") String nickname) {
		return memberService.checkValidNickname(nickname);
	}
	
	@PostMapping("/validEmail")
	@ResponseBody
	public String validEmail(@RequestParam("email") String email) {
		return memberService.checkValidEmail(email);
	}
	
	@PostMapping("/checkRole")
	@ResponseBody
	public String checkRole(@RequestParam("memberId") String memberId) {
		return memberService.checkRole(memberId);
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute MemberDTO memberDTO) {
		memberService.createMember(memberDTO);
		return "redirect:/mediquanta/member/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
		String isValidMember = "n";
		if (memberService.login(memberDTO)) {
			HttpSession session = request.getSession();
			session.setAttribute("memberId", memberDTO.getMemberId());
			session.setAttribute("role", checkRole(memberDTO.getMemberId()));
			isValidMember = "y";
		}
		return isValidMember;
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(); 
		session.invalidate();
		
		return "redirect:/mediquanta";
		
	}
	
	@GetMapping("/profile")
	public String redirectProfile(Model model, HttpSession session) {
		String role = (String)session.getAttribute("role");
		String page = role.equals("USER") ? "userProfile" : "adminProfile";
		
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		return "/member/" + page;
	}
	
}
