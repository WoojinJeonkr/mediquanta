package com.application.mediquanta.post.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.post.dto.PostDTO;

@Mapper
public interface PostDAO {

	public List<PostDTO> getPostList();
	public List<PostDTO> getRecentPostList();
	public List<Map<String, Object>> getPostListCount();
	public void updateReplyCntPlus(long postId);
	public void updateReplyCntMinus(long postId);
	public void createPost(PostDTO postDTO);
	public PostDTO findPostDetail(long postId);
	public void updateViewCnt(long postId);
	public void updatePost(PostDTO postDTO);
	public void deletePost(long postId);
	
}
