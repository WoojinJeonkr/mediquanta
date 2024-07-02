package com.application.mediquanta.bookmark.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.bookmark.dto.BookmarkDTO;

@Mapper
public interface BookmarkDAO {

	public BookmarkDTO findByMemberId(String memberId);
	public void addHospitalBookmark(BookmarkDTO bookmarkDTO);
	public void addPharmacyBookmark(BookmarkDTO bookmarkDTO);
	public void removeHospitalBookmark(BookmarkDTO bookmarkDTO);
	public void removePharmacyBookmark(BookmarkDTO bookmark);
	
}
