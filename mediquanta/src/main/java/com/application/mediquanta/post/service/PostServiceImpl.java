package com.application.mediquanta.post.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.post.dao.PostDAO;
import com.application.mediquanta.post.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;
	
	@Override
	public void createPost(PostDTO postDTO) {
		postDTO.setCreatedAt(new Date());
		postDAO.createPost(postDTO);
	}

}
