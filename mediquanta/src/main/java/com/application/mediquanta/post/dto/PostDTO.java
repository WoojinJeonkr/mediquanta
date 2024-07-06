package com.application.mediquanta.post.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostDTO {

	private long postId;
	private String communityName;
	private String title;
	private String author;
	private String content;
	private long replyCnt;
	private long viewCnt;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
	
}
