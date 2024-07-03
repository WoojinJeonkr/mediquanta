package com.application.mediquanta.hospital.bookmark.service;

import java.util.List;

import com.application.mediquanta.hospital.bookmark.dto.HospitalBookmarkDTO;

public interface HospitalBookmarkService {
	public List<HospitalBookmarkDTO> getBookmarksForMember(String memberId);		// 북마크 조회
	public void addHospitalBookmark(String memberId, long hospitalId); 				// 병원 북마크 추가
	public void removeHospitalBookmark(String memberId, long hospitalId); 			// 병원 북마크 제거
}
