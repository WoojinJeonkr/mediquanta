package com.application.mediquanta.hospital.bookmark.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.hospital.bookmark.dto.HospitalBookmarkDTO;

@Mapper
public interface HospitalBookmarkDAO {

	public List<HospitalBookmarkDTO> findByMemberIdx(long memberIdx);
	public void addHospitalBookmark(HospitalBookmarkDTO hospitalBookmarkDTO);
	public void removeHospitalBookmark(Map<String, Object> params);
	
}
