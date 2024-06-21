package com.application.mediquanta.member.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Value("${file.repo.path}")
    private String fileRepositoryPath;

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
	public String register(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfile, memberDTO);
		return "member/login";
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
			memberDTO.setLastLogin(new Date());
			
		}
		return isValidMember;
	}
	
	@GetMapping("/forgotPasswd")
	public String tempPasswd() {
		return "member/forgotPasswdForm";
	}
	
	@PostMapping("/forgotPasswd")
	public String forgotPasswd(@RequestParam("memberId") String memberId) {
		System.out.println(memberId);
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		String page = "";
		if (memberDTO == null) {
			page = "/member/register";
		} else {
			// TODO : 1. 이메일 연동해서 사용자의 이메일로 임시 비밀번호를 보내는 기능 개발 진행 예정
			// https://velog.io/@tjddus0302/Spring-Boot-%EB%A9%94%EC%9D%BC-%EB%B0%9C%EC%86%A1-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-Gmail
			String email = memberDTO.getEmail();
			System.out.println(email);
			page = "/member/login";
		}
		return "redirect:" + page;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	
	@GetMapping("/thumbnails")
    @ResponseBody
    public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException{
        return new UrlResource("file:" + fileRepositoryPath + fileName);
    }
	
	@GetMapping("/profile")
	public String redirectProfile(Model model, HttpSession session) {
		String role = (String)session.getAttribute("role");
		String page = "";
		if (role == null) {
			page = "redirect:/";
		} else {
			String profilePage = role.equals("USER") ? "userProfile" : "adminProfile";
			
			String memberId = (String)session.getAttribute("memberId");
			model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
			page = "member/" + profilePage;
		}
		
		return page;
	}
	
	@GetMapping("/updateProfile")
	public String updateProfile(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		return "member/updateProfile";
	}
	
	@PostMapping("/updateProfile")
	public String updateProfile(@RequestParam("uploadProfile") MultipartFile uploadProfile, MemberDTO memberDTO, HttpSession session) throws IllegalStateException, IOException {
		String role = (String)session.getAttribute("role");
		memberService.updateMember(uploadProfile, memberDTO);
		String profilePage = role.equals("USER") ? "userProfile" : "adminProfile";
		return "member/" + profilePage;
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		memberService.signOut(memberId);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/memberList")
	public String getMemberList(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		model.addAttribute("memberList", memberService.getMemberList());
		model.addAttribute("memberRoleCount", memberService.getRoleCount());
		model.addAttribute("memberGenderCount", memberService.getGenderCount());
		model.addAttribute("memberActiveCount", memberService.getActiveCount());
		return "member/memberList";
	}
	
}
