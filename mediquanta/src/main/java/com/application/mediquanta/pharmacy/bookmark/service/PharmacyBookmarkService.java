package com.application.mediquanta.pharmacy.bookmark.service;

import java.util.List;

import com.application.mediquanta.pharmacy.bookmark.dto.PharmacyBookmarkDTO;

public interface PharmacyBookmarkService {
	public List<PharmacyBookmarkDTO> getBookmarksForMember(String memberId);		// 북마크 조회
	public void addPharmacyBookmark(String memberId, long pharmacyId); 				// 약국 북마크 추가
	public void removePharmacyBookmark(String memberId, long pharmacyId); 			// 약국 북마크 제거
}
