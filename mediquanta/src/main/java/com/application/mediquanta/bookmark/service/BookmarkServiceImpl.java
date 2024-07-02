package com.application.mediquanta.bookmark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.bookmark.dao.BookmarkDAO;
import com.application.mediquanta.bookmark.dto.BookmarkDTO;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	
	// TODO 1. 북마크 백엔드랑 프론트엔드 연결
	// hospitalList.html (widget-26-hospital-starred : click -> addHospitalBookmark, cancel -> removeHospitalBookmark)
	// pharmacyList.html (widget-26-pharmacy-starred : click -> addPharmacyBookmark, cancel -> removePharmacyBookmark)
	
	@Autowired
    private BookmarkDAO bookmarkDAO;

	@Override
	public void addHospitalBookmark(String memberId, long hospitalId) {
		BookmarkDTO existingBookmarkDTO = bookmarkDAO.findByMemberId(memberId);
		if (existingBookmarkDTO == null) {
			BookmarkDTO bookmarkDTO = new BookmarkDTO();
			bookmarkDTO.setMemberId(memberId);
			List<Long> emptyHospitalBookmarkList = new ArrayList<>();
			emptyHospitalBookmarkList.add(hospitalId);
			bookmarkDTO.setHospitalBookmarks(emptyHospitalBookmarkList);
			bookmarkDAO.addHospitalBookmark(bookmarkDTO);
		} else {
			List<Long> hospitalBookmarkList = existingBookmarkDTO.getHospitalBookmarks();
			hospitalBookmarkList.add(hospitalId);
			existingBookmarkDTO.setHospitalBookmarks(hospitalBookmarkList);
			bookmarkDAO.addHospitalBookmark(existingBookmarkDTO);
		}
	}

	@Override
	public void addPharmacyBookmark(String memberId, long pharmacyId) {
		BookmarkDTO existingBookmarkDTO = bookmarkDAO.findByMemberId(memberId);
		if (existingBookmarkDTO == null) {
			BookmarkDTO bookmarkDTO = new BookmarkDTO();
			bookmarkDTO.setMemberId(memberId);
			List<Long> emptyPharmacyBookmarkList = new ArrayList<>();
			emptyPharmacyBookmarkList.add(pharmacyId);
			bookmarkDTO.setPharmacyBookmarks(emptyPharmacyBookmarkList);
			bookmarkDAO.addPharmacyBookmark(bookmarkDTO);
		} else {
			List<Long> pharmacyBookmarkList = existingBookmarkDTO.getPharmacyBookmarks();
			pharmacyBookmarkList.add(pharmacyId);
			existingBookmarkDTO.setPharmacyBookmarks(pharmacyBookmarkList);
			bookmarkDAO.addPharmacyBookmark(existingBookmarkDTO);
		}
	}

	@Override
	public void removeHospitalBookmark(String memberId, long hospitalId) {
		BookmarkDTO existingBookmarkDTO = bookmarkDAO.findByMemberId(memberId);
		
		if (existingBookmarkDTO != null && existingBookmarkDTO.getHospitalBookmarks() != null) {
			List<Long> hospitalBookmarkList = existingBookmarkDTO.getHospitalBookmarks();
			hospitalBookmarkList = hospitalBookmarkList.stream()
						        .filter(id -> !id.equals(hospitalId))
						        .collect(Collectors.toList());
			existingBookmarkDTO.setHospitalBookmarks(hospitalBookmarkList);
			bookmarkDAO.updateBookmark(existingBookmarkDTO);
		}
	}

	@Override
	public void removePharmacyBookmark(String memberId, long pharmacyId) {
		BookmarkDTO existingBookmarkDTO = bookmarkDAO.findByMemberId(memberId);
		
		if (existingBookmarkDTO != null && existingBookmarkDTO.getPharmacyBookmarks() != null) {
			List<Long> pharmacyBookmarkList = existingBookmarkDTO.getPharmacyBookmarks();
			pharmacyBookmarkList = pharmacyBookmarkList.stream()
						        .filter(id -> !id.equals(pharmacyId))
						        .collect(Collectors.toList());
			existingBookmarkDTO.setPharmacyBookmarks(pharmacyBookmarkList);
			bookmarkDAO.updateBookmark(existingBookmarkDTO);
		}
	}
	
	@Override
	public BookmarkDTO getBookmarksForMember(String memberId) {
		return bookmarkDAO.findByMemberId(memberId);
	}

}
