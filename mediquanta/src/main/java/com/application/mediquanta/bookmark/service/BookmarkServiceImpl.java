package com.application.mediquanta.bookmark.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.bookmark.dao.BookmarkDAO;
import com.application.mediquanta.bookmark.dto.BookmarkDTO;
import com.application.mediquanta.member.dao.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

	// TODO 1. 북마크 화면 단에서 보여지도록 표시
	// hospitalList.html (widget-26-hospital-starred : click -> addHospitalBookmark,
	// cancel -> removeHospitalBookmark)
	// pharmacyList.html (widget-26-pharmacy-starred : click -> addPharmacyBookmark,
	// cancel -> removePharmacyBookmark)
	
	@Autowired
	private BookmarkDAO bookmarkDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public List<BookmarkDTO> getBookmarksForMember(String memberId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<BookmarkDTO> bookmarkList = bookmarkDAO.findByMemberIdx(memberIdx);
		if (bookmarkList.size() > 0) {
			return bookmarkList;
		} else {
			return null;
		}
	}

	@Override
	public void addHospitalBookmark(String memberId, long hospitalId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<BookmarkDTO> bookmarkList = bookmarkDAO.findByMemberIdx(memberIdx);
		boolean alreadyBookmarked = bookmarkList.stream().anyMatch(bookmark -> bookmark.getHospitalId() == hospitalId);
		if (!alreadyBookmarked) {
			BookmarkDTO newBookmark = new BookmarkDTO();
			newBookmark.setMemberIdx(memberIdx);
			newBookmark.setHospitalId(hospitalId);
			bookmarkDAO.addHospitalBookmark(newBookmark);
		}
	}

	@Override
	public void removeHospitalBookmark(String memberId, long hospitalId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<BookmarkDTO> bookmarkList = bookmarkDAO.findByMemberIdx(memberIdx);
		Optional<BookmarkDTO> bookmarkToRemove = bookmarkList.stream()
                .filter(bookmark -> bookmark.getHospitalId() == hospitalId)
                .findFirst();
        bookmarkToRemove.ifPresent(bookmark -> bookmarkDAO.removeHospitalBookmark(bookmark.getBookmarkId()));
	}

	// TODO 2. 약국 목록에서의 북마크 추가 / 삭제 기능 구현
	
	/*
	 * @Override public void addPharmacyBookmark(String memberId, long pharmacyId) {
	 * long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx(); BookmarkDTO
	 * existingBookmarkDTO = bookmarkDAO.findByMemberIdx(memberIdx); if
	 * (existingBookmarkDTO == null) { BookmarkDTO bookmarkDTO = new BookmarkDTO();
	 * bookmarkDTO.setMemberIdx(memberIdx); List<Long> emptyPharmacyBookmarkList =
	 * new ArrayList<>(); emptyPharmacyBookmarkList.add(pharmacyId);
	 * bookmarkDTO.setPharmacyBookmarks(emptyPharmacyBookmarkList);
	 * bookmarkDAO.addPharmacyBookmark(bookmarkDTO); } else { List<Long>
	 * pharmacyBookmarkList = existingBookmarkDTO.getPharmacyBookmarks();
	 * pharmacyBookmarkList.add(pharmacyId);
	 * existingBookmarkDTO.setPharmacyBookmarks(pharmacyBookmarkList);
	 * bookmarkDAO.addPharmacyBookmark(existingBookmarkDTO); } }
	 */

	/*
	 * @Override public void removePharmacyBookmark(String memberId, long
	 * pharmacyId) { long memberIdx =
	 * memberDAO.getUserInfo(memberId).getMemberIdx(); BookmarkDTO
	 * existingBookmarkDTO = bookmarkDAO.findByMemberIdx(memberIdx);
	 * 
	 * if (existingBookmarkDTO != null && existingBookmarkDTO.getPharmacyBookmarks()
	 * != null) { List<Long> pharmacyBookmarkList =
	 * existingBookmarkDTO.getPharmacyBookmarks(); pharmacyBookmarkList =
	 * pharmacyBookmarkList.stream() .filter(id -> !id.equals(pharmacyId))
	 * .collect(Collectors.toList());
	 * existingBookmarkDTO.setPharmacyBookmarks(pharmacyBookmarkList);
	 * bookmarkDAO.removePharmacyBookmark(existingBookmarkDTO); } }
	 */

}
