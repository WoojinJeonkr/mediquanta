package com.application.mediquanta.post.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.post.dto.PostDTO;

@Mapper
public interface PostDAO {

	public void createPost(PostDTO postDTO);
	
}
