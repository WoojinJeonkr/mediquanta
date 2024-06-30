package com.application.mediquanta.member.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

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

import com.application.mediquanta.email.entity.EmailMessage;
import com.application.mediquanta.email.service.EmailService;
import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;
import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;
import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.pharmacy.service.PharmacyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Value("${file.repo.path}")
    private String fileRepositoryPath;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
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
		}
		return isValidMember;
	}
	
	@GetMapping("/forgotPasswd")
	public String tempPasswd() {
		return "member/forgotPasswdForm";
	}
	
	@PostMapping("/forgotPasswd")
	public String forgotPasswd(@RequestParam("memberId") String memberId) {
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		String page = "";
		if (memberDTO == null) {
			page = "/member/register";
		} else {
			String email = memberDTO.getEmail();
			EmailMessage emailMessage = EmailMessage.builder()
	                .to(email)
	                .subject("[Mediquanta] 임시 비밀번호 발급")
	                .build();

	        emailService.sendMail(emailMessage, "email");
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
		String profilePage = role.equals("USER") ? "userProfile" : "adminProfile";
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		Map<String, Double> location = memberService.kakaoLocalAPI(memberDTO.getRoadAddress());
		List<HospitalDTO> hospitals = hospitalService.selectNearestHospitals(location.get("latitude").doubleValue(), location.get("longitude").doubleValue());
		List<PharmacyDTO> pharmacies = pharmacyService.selectNearestPharmacies(location.get("latitude").doubleValue(), location.get("longitude").doubleValue());
		List<Map<String, Object>> hospitalTypeCounts = hospitalService.getHospitalTypeCounts();
        
		model.addAttribute("memberDTO", memberDTO);
		model.addAttribute("hospitals", hospitals);
		model.addAttribute("pharmacies", pharmacies);
		model.addAttribute("hospitalTypeCounts", hospitalTypeCounts);
		return "member/" + profilePage;
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
