package com.application.mediquanta.bookmark.dto;

import lombok.Data;

@Data
public class BookmarkDTO {
    private long bookmarkId;
    private long memberIdx;
    private long hospitalId;
    private long pharmacyId;
}
