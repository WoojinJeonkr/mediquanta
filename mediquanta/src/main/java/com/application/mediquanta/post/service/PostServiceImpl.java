package com.application.mediquanta.post.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.post.dao.PostDAO;
import com.application.mediquanta.post.dto.PostDTO;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;
	
	@Override
	public List<PostDTO> getPostList() {
		List<PostDTO> postList = postDAO.getPostList();
		return postList != null ? postList : Collections.emptyList();
	}
	
	@Override
	public List<PostDTO> getRecentPostList() {
		List<PostDTO> postList = postDAO.getRecentPostList();
		return postList != null ? postList : Collections.emptyList();
	}

	@Override
	public long getPostListCount(String communityName) {
		List<Map<String, Object>> postListCounts = postDAO.getPostListCount();
	    for (Map<String, Object> postListCount : postListCounts) {
	        if (communityName.equals(postListCount.get("COMMUNITY_NAME"))) {
	            return Long.parseLong(postListCount.get("COUNT").toString());
	        }
	    }
	    return 0;
	}

	@Override
	public void createPost(String communityName, PostDTO postDTO) {
		postDTO.setCommunityName(communityName);
		postDTO.setReplyCnt(0);
		postDTO.setViewCnt(0);
		postDTO.setCreatedAt(new Date());
		postDAO.createPost(postDTO);
	}

	@Override
	public PostDTO findPostDetail(long postId) {
		PostDTO post = postDAO.findPostDetail(postId);
		postDAO.updateViewCnt(postId);
		return post;
	}

	@Override
	public void updatePost(PostDTO postDTO) {
		postDTO.setUpdatedAt(new Date());
		postDAO.updatePost(postDTO);
	}

	@Override
	public void deletePost(long postId) {
		postDAO.deletePost(postId);
	}

}
