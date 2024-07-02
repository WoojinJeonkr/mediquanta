package com.application.mediquanta.bookmark.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookmarkDTO {

	private Long bookmarkId;
    private String memberId;
    private List<Long> hospitalBookmarks;
    private List<Long> pharmacyBookmarks;
    
}
