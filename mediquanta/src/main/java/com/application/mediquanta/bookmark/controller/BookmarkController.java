package com.application.mediquanta.bookmark.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Long> getHospitalBookmarks(HttpSession session) {
        String memberId = (String)session.getAttribute("memberId");
        BookmarkDTO bookmarkDTO = bookmarkService.getBookmarksForMember(memberId);
        
        if (bookmarkDTO == null) {
            return new ArrayList<>();
        }

        List<Long> bookmarkHospList = bookmarkDTO.getHospitalBookmarks();
        if (bookmarkHospList == null) {
            bookmarkHospList = new ArrayList<>();
        }
        
        return bookmarkHospList;
    }

    @PostMapping("/hospital/addHospitalBookmark")
    @ResponseBody
    public ResponseEntity<Void> addHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
    	log.info("add 호출!");
        String memberId = (String)session.getAttribute("memberId");
        log.info("memberId -> {}", memberId);
        log.info("request -> {}", request);
        Long hospitalId = request.get("hospitalId");
        log.info("hospitalId -> {}", hospitalId);
        log.info("type -> {}", request.get("hospitalId").getClass().getName());
        bookmarkService.addHospitalBookmark(memberId, hospitalId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hospital/removeHospitalBookmark")
    @ResponseBody
    public ResponseEntity<Void> removeHospitalBookmark(@RequestBody Map<String, Long> request, HttpSession session) {
        String memberId = (String)session.getAttribute("memberId");
        long hospitalId = request.get("hospitalId");
        bookmarkService.removeHospitalBookmark(memberId, hospitalId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/pharmacy/add")
    @ResponseBody
    public ResponseEntity<Void> addPharmacyBookmark(@RequestBody Map<String, Long> request,  HttpSession session) {
    	String memberId = (String)session.getAttribute("memberId");
        bookmarkService.addPharmacyBookmark(memberId, request.get("memberId"));
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/pharmacy/remove")
    @ResponseBody
    public ResponseEntity<Void> removePharmacyBookmark(@RequestParam("pharmacyId") long pharmacyId,  HttpSession session) {
    	String memberId = (String)session.getAttribute("memberId");
        bookmarkService.removePharmacyBookmark(memberId, pharmacyId);
        return ResponseEntity.ok().build();
    }
    
}
