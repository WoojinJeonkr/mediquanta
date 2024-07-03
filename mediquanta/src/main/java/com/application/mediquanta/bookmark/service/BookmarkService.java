package com.application.mediquanta.bookmark.service;

import java.util.List;

import com.application.mediquanta.bookmark.dto.BookmarkDTO;

public interface BookmarkService {

	public List<BookmarkDTO> getBookmarksForMember(String memberId);				// 북마크 조회
	public void addHospitalBookmark(String memberId, long hospitalId); 				// 병원 북마크 추가
	// public void addPharmacyBookmark(String memberId, long pharmacyId); // 약국 북마크 추가
	public void removeHospitalBookmark(String memberId, long hospitalId); // 병원 북마크 제거
	// public void removePharmacyBookmark(String memberId, long pharmacyId); // 약국 북마크 제거
}
