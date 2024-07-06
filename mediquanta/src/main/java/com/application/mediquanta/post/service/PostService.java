package com.application.mediquanta.post.service;

import java.util.List;

import com.application.mediquanta.post.dto.PostDTO;

public interface PostService {

	public List<PostDTO> getPostList();
	public List<PostDTO> getRecentPostList();
	public long getPostListCount(String communityName);
	public void createPost(String communityName, PostDTO postDTO);
	public PostDTO findPostDetail(long postId);
	public void updatePost(PostDTO postDTO);
	public void deletePost(long postId);

}
