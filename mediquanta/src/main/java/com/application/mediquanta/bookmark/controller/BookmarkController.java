package com.application.mediquanta.bookmark.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.bookmark.dto.BookmarkDTO;
import com.application.mediquanta.bookmark.service.BookmarkService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bookmark")
@Slf4j
public class BookmarkController {

	@Autowired
	private BookmarkService bookmarkService;

	@PostMapping("/hospital/getHospitalBookmarks")
	@ResponseBody
	public List<BookmarkDTO> getHospitalBookmarks(HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarksForMember(memberId);
		return bookmarkList;
	}

	@PostMapping("/hospital/addHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> addHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		bookmarkService.addHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/hospital/removeHospitalBookmark")
	@ResponseBody
	public ResponseEntity<Void> removeHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		Long hospitalId = request.get("hospitalId");
		bookmarkService.removeHospitalBookmark(memberId, hospitalId);
		return ResponseEntity.ok().build();
	}

	/*
	 * @PostMapping("/pharmacy/addPharmacyBookmark")
	 * 
	 * @ResponseBody public ResponseEntity<Void> addPharmacyBookmark(@RequestBody
	 * Map<String, Long> request, HttpSession session) { String memberId = (String)
	 * session.getAttribute("memberId"); Long pharmacyId =
	 * request.get("pharmacyId"); bookmarkService.addPharmacyBookmark(memberId,
	 * pharmacyId); return ResponseEntity.ok().build(); }
	 * 
	 * @PostMapping("/pharmacy/removePharmacyBookmark")
	 * 
	 * @ResponseBody public ResponseEntity<Void> removePharmacyBookmark(@RequestBody
	 * Map<String, Long> request, HttpSession session) { String memberId = (String)
	 * session.getAttribute("memberId"); Long pharmacyId =
	 * request.get("pharmacyId"); bookmarkService.removePharmacyBookmark(memberId,
	 * pharmacyId); return ResponseEntity.ok().build(); }
	 */

}
