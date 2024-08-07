package com.application.mediquanta.hospital.bookmark.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "병원 북마크 DTO")
public class HospitalBookmarkDTO {
	
	@Schema(description="북마크 인덱스", example="1")
    private long bookmarkId;
	
	@Schema(description="회원 인덱스", example="1")
    private long memberIdx;
	
	@Schema(description="병원 인덱스", example="1")
    private long hospitalId;
}
