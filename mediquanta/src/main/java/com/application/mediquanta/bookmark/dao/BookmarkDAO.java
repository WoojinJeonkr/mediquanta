package com.application.mediquanta.bookmark.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.bookmark.dto.BookmarkDTO;

@Mapper
public interface BookmarkDAO {

	public List<BookmarkDTO> findByMemberIdx(long memberIdx);
	public void addHospitalBookmark(BookmarkDTO bookmarkDTO);
	public void addPharmacyBookmark(BookmarkDTO bookmarkDTO);
	public void removeHospitalBookmark(long bookmarkId);
	public void removePharmacyBookmark(BookmarkDTO bookmarkDTO);
	
}
