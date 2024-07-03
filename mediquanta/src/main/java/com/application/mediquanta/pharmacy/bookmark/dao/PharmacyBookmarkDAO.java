package com.application.mediquanta.pharmacy.bookmark.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.pharmacy.bookmark.dto.PharmacyBookmarkDTO;

@Mapper
public interface PharmacyBookmarkDAO {

	public List<PharmacyBookmarkDTO> findByMemberIdx(long memberIdx);
	public void addPharmacyBookmark(PharmacyBookmarkDTO pharmacyBookmarkDTO);
	public void removePharmacyBookmark(Map<String, Object> params);
	
}
