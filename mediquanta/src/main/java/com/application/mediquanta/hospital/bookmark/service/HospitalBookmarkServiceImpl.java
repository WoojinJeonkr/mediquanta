package com.application.mediquanta.hospital.bookmark.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.hospital.bookmark.dao.HospitalBookmarkDAO;
import com.application.mediquanta.hospital.bookmark.dto.HospitalBookmarkDTO;
import com.application.mediquanta.member.dao.MemberDAO;

@Service
public class HospitalBookmarkServiceImpl implements HospitalBookmarkService {

	@Autowired
	private HospitalBookmarkDAO hospitalBookmarkDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public List<HospitalBookmarkDTO> getBookmarksForMember(String memberId) {
		if (memberId != null) {
			long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
			List<HospitalBookmarkDTO> bookmarkList = hospitalBookmarkDAO.findByMemberIdx(memberIdx);
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
	public void addHospitalBookmark(String memberId, long hospitalId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<HospitalBookmarkDTO> bookmarkList = hospitalBookmarkDAO.findByMemberIdx(memberIdx);
		boolean alreadyBookmarked = bookmarkList.stream().anyMatch(bookmark -> bookmark.getHospitalId() == hospitalId);
		if (!alreadyBookmarked) {
			HospitalBookmarkDTO newBookmark = new HospitalBookmarkDTO();
			newBookmark.setMemberIdx(memberIdx);
			newBookmark.setHospitalId(hospitalId);
			hospitalBookmarkDAO.addHospitalBookmark(newBookmark);
		}
	}

	@Override
	public void removeHospitalBookmark(String memberId, long hospitalId) {
		long memberIdx = memberDAO.getUserInfo(memberId).getMemberIdx();
		List<HospitalBookmarkDTO> bookmarkList = hospitalBookmarkDAO.findByMemberIdx(memberIdx);
		Optional<HospitalBookmarkDTO> bookmarkToRemove = bookmarkList.stream()
				.filter(bookmark -> bookmark.getHospitalId() == hospitalId).findFirst();
		bookmarkToRemove.ifPresent(bookmark -> {
			Map<String, Object> params = new HashMap<>();
			params.put("memberIdx", memberIdx);
			params.put("hospitalId", hospitalId);
			hospitalBookmarkDAO.removeHospitalBookmark(params);
		});
	}

}
