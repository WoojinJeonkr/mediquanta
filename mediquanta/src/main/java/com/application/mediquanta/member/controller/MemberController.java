package com.application.mediquanta.member.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.application.mediquanta.community.service.CommunityService;
import com.application.mediquanta.email.entity.EmailMessage;
import com.application.mediquanta.email.service.EmailService;
import com.application.mediquanta.hospital.bookmark.service.HospitalBookmarkService;
import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;
import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;
import com.application.mediquanta.pharmacy.bookmark.service.PharmacyBookmarkService;
import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.pharmacy.service.PharmacyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@Tag(name="Member", description="회원 Api")
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
	
	@Autowired
	private HospitalBookmarkService hospitalBookmarkService;
	
	@Autowired
	private PharmacyBookmarkService pharmacyBookmarkService;
	
	@Autowired
	private CommunityService communityService;
	
	@GetMapping("/register")
	@Operation(summary="페이지 이동 - 회원 등록", description="회원 등록 페이지로 이동")
	public String register() {
		return "member/register";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	@Operation(summary="아이디 확인", description="아이디를 입력받아 DB에 아이디가 존재하는지 확인")
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
	
	@PostMapping("/validNickname")
	@ResponseBody
	@Operation(summary="닉네임 확인", description="닉네임을 입력받아 DB에 닉네임이 존재하는지 확인")
	public String validNickname(@RequestParam("nickname") String nickname) {
		return memberService.checkValidNickname(nickname);
	}
	
	@PostMapping("/validEmail")
	@ResponseBody
	@Operation(summary="이메일 확인", description="이메일을 입력받아 DB에 이메일이 존재하는지 확인")
	public String validEmail(@RequestParam("email") String email) {
		return memberService.checkValidEmail(email);
	}
	
	@PostMapping("/checkRole")
	@ResponseBody
	@Operation(summary="권한 확인", description="아이디를 입력받아 권한이 있는지 확인")
	public String checkRole(@RequestParam("memberId") String memberId) {
		return memberService.checkRole(memberId);
	}
	
	@PostMapping("/register")
	@Operation(summary="회원 등록", description="사용자가 입력받은 정보를 토대로 회원 등록")
	public String register(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfile, memberDTO);
		return "member/login";
	}
	
	@GetMapping("/login")
	@Operation(summary="페이지 이동 - 로그인", description="로그인 페이지로 이동")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	@Operation(summary="로그인", description="아이디와 비밀번호를 입력받아 로그인")
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
	@Operation(summary="페이지 이동 - 비밀번호 찾기", description="비밀번호 찾기 페이지로 이동")
	public String tempPasswd() {
		return "member/forgotPasswdForm";
	}
	
	@PostMapping("/forgotPasswd")
	@Operation(summary="비밀번호 찾기", description="아이디를 통해 DB에 회원 정보가 있다면 임시 비밀번호를 회원의 이메일로 발송")
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
	@Operation(summary="로그아웃", description="로그아웃 버튼 클릭 시 로그아웃 후 메인 화면으로 이동")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	
	@GetMapping("/thumbnails")
    @ResponseBody
    @Operation(summary="프로필 업로드", description="프로필 이미지 업로드")
    public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException{
        return new UrlResource("file:" + fileRepositoryPath + fileName);
    }
	
	@GetMapping("/profile")
	@Operation(summary="페이지 이동 - 프로필", description="프로필 페이지로 이동")
	public String redirectProfile(Model model, HttpSession session) {
		String role = (String)session.getAttribute("role");
		String profilePage = role.equals("USER") ? "userProfile" : "adminProfile";
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		Map<String, Double> location = memberService.kakaoLocalAPI(memberDTO.getRoadAddress());
		List<HospitalDTO> hospitals = hospitalService.selectNearestHospitals(location.get("latitude").doubleValue(), location.get("longitude").doubleValue());
		List<PharmacyDTO> pharmacies = pharmacyService.selectNearestPharmacies(location.get("latitude").doubleValue(), location.get("longitude").doubleValue());
		List<Map<String, Object>> hospitalTypeCounts = hospitalService.getHospitalTypeCounts();
		List<Map<String, Object>> pharmacyTypeCounts = pharmacyService.getPharmacyTypeCounts();
        
		model.addAttribute("memberDTO", memberDTO);
		model.addAttribute("hospitals", hospitals);
		model.addAttribute("pharmacies", pharmacies);
		model.addAttribute("hospitalTypeCounts", hospitalTypeCounts);
		model.addAttribute("pharmacyTypeCounts", pharmacyTypeCounts);
		return "member/" + profilePage;
	}
	
	@GetMapping("/updateProfile")
	@Operation(summary="페이지 이동 - 회원 정보 수정", description="프로필 수정 페이지로 이동")
	public String updateProfile(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		return "member/updateProfile";
	}
	
	@PostMapping("/updateProfile")
	@Operation(summary="회원 정보 수정", description="회원이 입력한 회원 정보로 회원 정보 수정")
	public String updateProfile(@RequestParam("uploadProfile") MultipartFile uploadProfile, MemberDTO memberDTO, HttpSession session) throws IllegalStateException, IOException {
		memberService.updateMember(uploadProfile, memberDTO);
		return "redirect:/member/profile";
	}
	
	@GetMapping("/signOut")
	@Operation(summary="회원 탈퇴", description="프로필 화면에서 회원 탈퇴 버튼 클릭시 회원 비활성화")
	public String signOut(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		memberService.signOut(memberId);
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/memberList")
	@Operation(summary="페이지 이동 - 회원 관리", description="회원 관리 페이지로 이동")
	public String getMemberList(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		model.addAttribute("memberList", memberService.getMemberList());
		model.addAttribute("memberRoleCount", memberService.getRoleCount());
		model.addAttribute("memberGenderCount", memberService.getGenderCount());
		model.addAttribute("memberActiveCount", memberService.getActiveCount());
		return "member/memberList";
	}
	
	@GetMapping("/communityList")
	@Operation(summary="페이지 이동 - 커뮤니티 관리", description="커뮤니티 관리 페이지로 이동")
	public String getCommunityList(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		model.addAttribute("communityActiveCount", communityService.countActiveCommunity());
		model.addAttribute("communityList", communityService.getCommunityList());
		return "member/communityList";
	}
	
	@GetMapping("/bookmark")
	@Operation(summary="페이지 이동 - 북마크 관리", description="북마크 관리 페이지로 이동")
	public String getBookmarkList(HttpSession session, Model model) {
		String memberId = (String)session.getAttribute("memberId");
		List<HospitalDTO> hospitalBookmarkList = hospitalBookmarkService.getBookmarksForMember(memberId)
		        .stream()
		        .map(hospitalBookmark -> hospitalService.getHospitalDetails(hospitalBookmark.getHospitalId()))
		        .collect(Collectors.toList());

		List<PharmacyDTO> pharmacyBookmarkList = pharmacyBookmarkService.getBookmarksForMember(memberId)
		        .stream()
		        .map(pharmacyBookmark -> pharmacyService.getPharmacyDetails(pharmacyBookmark.getPharmacyId()))
		        .collect(Collectors.toList());
		model.addAttribute("memberDTO", memberService.getUserInfo(memberId));
		model.addAttribute("hospitalBookmarkList", hospitalBookmarkList);
		model.addAttribute("pharmacyBookmarkList", pharmacyBookmarkList);
		return "member/bookmarkList";
	}
	
	@GetMapping("/deleteMember")
	@Operation(summary="회원 정보 삭제", description="회원 탈퇴한 지 1년이 넘은 회원 정보들을 DB에서 삭제")
	public String deleteMember() {
		memberService.deleteMember();
		return "redirect:/member/memberList";
	}
	
}
