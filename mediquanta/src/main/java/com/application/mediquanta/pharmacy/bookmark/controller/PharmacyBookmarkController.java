package com.application.mediquanta.pharmacy.bookmark.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.pharmacy.bookmark.dto.PharmacyBookmarkDTO;
import com.application.mediquanta.pharmacy.bookmark.service.PharmacyBookmarkService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bookmark/pharmacy")
public class PharmacyBookmarkController {

	@Autowired
	private PharmacyBookmarkService pharmacybookmarkService;

	@PostMapping("/getPharmacyBookmarks")
	@ResponseBody
	public List<PharmacyBookmarkDTO> getHospitalBookmarks(HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		List<PharmacyBookmarkDTO> bookmarkList = pharmacybookmarkService.getBookmarksForMember(memberId);
		return bookmarkList;
	}


	@PostMapping("/addPharmacyBookmark")
	@ResponseBody
	public ResponseEntity<Void> addPharmacyBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long pharmacyId = request.get("pharmacyId");
		pharmacybookmarkService.addPharmacyBookmark(memberId, pharmacyId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/removePharmacyBookmark")
	@ResponseBody
	public ResponseEntity<Void> removePharmacyBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long pharmacyId = request.get("pharmacyId");
		pharmacybookmarkService.removePharmacyBookmark(memberId, pharmacyId);
		return ResponseEntity.ok().build();
	}

}
