package com.application.mediquanta.post.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PostDTO {

	private long postId;
	private String title;
	private String author;
	private String content;
	private Date createdAt;
	private Date updatedAt;
	
}
