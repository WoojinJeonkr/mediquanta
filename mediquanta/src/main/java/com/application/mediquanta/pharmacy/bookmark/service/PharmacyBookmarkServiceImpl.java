package com.application.mediquanta.pharmacy.bookmark.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.member.dao.MemberDAO;
import com.application.mediquanta.pharmacy.bookmark.dao.PharmacyBookmarkDAO;
import com.application.mediquanta.pharmacy.bookmark.dto.PharmacyBookmarkDTO;

@Service
public class PharmacyBookmarkServiceImpl implements PharmacyBookmarkService {

	// TODO 1. 북마크 화면 단에서 보여지도록 표시
	// pharmacyList.html (widget-26-pharmacy-starred : click -> addPharmacyBookmark,
	// cancel -> removePharmacyBookmark)

	@Autowired
	private PharmacyBookmarkDAO pharmacyBookmarkDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public List<PharmacyBookmarkDTO> getBookmarksForMember(String memberId) {
		if (memberId != null) {
			long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
			List<PharmacyBookmarkDTO> bookmarkList = pharmacyBookmarkDAO.findByMemberIdx(memberIdx);
			if (bookmarkList.size() > 0) {
				return bookmarkList;
			} else {
				return Collections.emptyList();
			}
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void addPharmacyBookmark(String memberId, long pharmacyId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<PharmacyBookmarkDTO> bookmarkList = pharmacyBookmarkDAO.findByMemberIdx(memberIdx);
		boolean alreadyBookmarked = bookmarkList.stream().anyMatch(bookmark -> bookmark.getPharmacyId() == pharmacyId);
		if (!alreadyBookmarked) {
			PharmacyBookmarkDTO newBookmark = new PharmacyBookmarkDTO();
			newBookmark.setMemberIdx(memberIdx);
			newBookmark.setPharmacyId(pharmacyId);
			pharmacyBookmarkDAO.addPharmacyBookmark(newBookmark);
		}
	}

	@Override
	public void removePharmacyBookmark(String memberId, long pharmacyId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<PharmacyBookmarkDTO> bookmarkList = pharmacyBookmarkDAO.findByMemberIdx(memberIdx);
		Optional<PharmacyBookmarkDTO> bookmarkToRemove = bookmarkList.stream()
				.filter(bookmark -> bookmark.getPharmacyId() == pharmacyId).findFirst();
		bookmarkToRemove.ifPresent(bookmark -> {
			Map<String, Object> params = new HashMap<>();
			params.put("memberIdx", memberIdx);
			params.put("pharmacyId", pharmacyId);
			pharmacyBookmarkDAO.removePharmacyBookmark(params);
		});
	}

}
