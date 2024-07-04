package com.application.mediquanta.community.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CommunityDTO {
	// TODO 1. 커뮤니티 기능 개발 예정
	private long communityId;
	private String communityName;
	private String description;
	private String purpose;
	private String activeYn;
	private long totalViewCount;
	private long totalMemberCount;
	private long totalPostCount;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
}
