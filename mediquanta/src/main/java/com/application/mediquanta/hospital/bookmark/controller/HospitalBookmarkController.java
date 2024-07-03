package com.application.mediquanta.hospital.bookmark.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.hospital.bookmark.dto.HospitalBookmarkDTO;
import com.application.mediquanta.hospital.bookmark.service.HospitalBookmarkService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bookmark/hospital")
public class HospitalBookmarkController {

	@Autowired
	private HospitalBookmarkService hospitalBookmarkService;

	@PostMapping("/getHospitalBookmarks")
	@ResponseBody
	public List<HospitalBookmarkDTO> getHospitalBookmarks(HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		List<HospitalBookmarkDTO> bookmarkList = hospitalBookmarkService.getBookmarksForMember(memberId);
		return bookmarkList;
	}

	@PostMapping("/addHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> addHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		hospitalBookmarkService.addHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/removeHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> removeHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		hospitalBookmarkService.removeHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

}
