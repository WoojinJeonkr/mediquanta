package com.application.mediquanta.post.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.application.mediquanta.post.dto.PostDTO;
import com.application.mediquanta.post.service.PostService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/{communityName}/post")
	public List<PostDTO> getPost(@PathVariable("communityName") String communityName, Model model) {
		/*
		List<PostDTO> postList = postService.findPostList();
		if (postList != null) {
			return postService.findPostList();
		} else {
			return Collections.emptyList();
		}
		*/
		return Collections.emptyList();
	}
	
	@GetMapping("/createPost")
	public String createPost() {
		return "post/createPost";
	}
	
	@PostMapping("/{communityName}/createPost")
	public String createPost(@PathVariable("communityName") String communityName, @RequestBody PostDTO postDTO) {
		postService.createPost(postDTO);
		return "redirect:/community/" + communityName;
	}
	
	@GetMapping("/{communityName}/{postId}")
	public String getPostDetail(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId, Model model) {
		// model.addAttribute("post", postService.findPostDetail());
		return "post/postDetail";
	}
	
	@GetMapping("/updatePost")
	public String updatePost() {
		return "post/updatePost";
	}
	
	@PostMapping("/{communityName}/{postId}/updatePost")
	public String updatePost(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId, @RequestBody PostDTO postDTO) {
		// postService.updatePost(postDTO);
		return "redirect:/community/" + communityName;
	}
	
}
