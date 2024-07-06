package com.application.mediquanta.community.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.community.dto.CommunityDTO;
import com.application.mediquanta.community.service.CommunityService;
import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;
import com.application.mediquanta.post.dto.PostDTO;
import com.application.mediquanta.post.service.PostService;
import com.application.mediquanta.reply.dto.ReplyDTO;
import com.application.mediquanta.reply.service.ReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private ReplyService replyService;

	@GetMapping
	public String viewLounge(HttpSession session, Model model) {
		String role = (String)session.getAttribute("role");
		if (role != null) model.addAttribute("role", role);
		List<CommunityDTO> activeCommunities = communityService.getCommunityList().stream()
		        .filter(community -> "y".equals(community.getActiveYn()))
		        .collect(Collectors.toList());
		model.addAttribute("communityList", activeCommunities);
		return "community/lounge";
	}
	
	@GetMapping("/createCommunity")
	public String createCommunity() {
		return "community/communityCreate";
	}
	
	@PostMapping("/validCommunityName")
	@ResponseBody
	public String validCommunityName(@RequestParam("communityName") String communityName) {
		return communityService.validCommunityName(communityName);
	}
	
	@PostMapping("/createCommunity")
	public String createCommunity(@ModelAttribute CommunityDTO communityDTO) {
		communityService.createCommunity(communityDTO);
		return "redirect:/community";
	}
	
	@PostMapping("/agreeCommunity")
	@ResponseBody
	public void agreeCommunity(@RequestParam("communityId") long communityId) {
		communityService.agreeCommunity(communityId);
	}
	
	@GetMapping("/{communityName}")
    public String viewCommunityPage(@PathVariable("communityName") String communityName, Model model, HttpSession session) {
		String role = (String)session.getAttribute("role");
		CommunityDTO community = communityService.findCommunityByCommunityName(communityName);
		List<PostDTO> postList = postService.getPostList();
		List<PostDTO> filteredPostList = postList.stream()
	                .filter(post -> communityName.equals(post.getCommunityName()))
	                .collect(Collectors.toList());
		List<PostDTO> recentPostList = postService.getRecentPostList();
		List<PostDTO> filteredRecentPostList = recentPostList.stream()
                .filter(post -> communityName.equals(post.getCommunityName()))
                .collect(Collectors.toList());
		long postListCount = postService.getPostListCount(communityName);
		long communityMemberCount = replyService.getReplyMemberCount(communityName);
		model.addAttribute("community", community);
		model.addAttribute("role", role);
		model.addAttribute("postList", filteredPostList);
		model.addAttribute("recentPostList", filteredRecentPostList);
		model.addAttribute("postListCounts", postListCount);
		model.addAttribute("communityMemberCount", communityMemberCount);
		communityService.updateViewCnt(communityName);
		return "community/forum";
	}	
	
	@GetMapping("/{communityName}/createPost")
	public String createPost(@PathVariable("communityName") String communityName, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		model.addAttribute("author", memberDTO.getNickname());
		model.addAttribute("communityName", communityName);
		return "post/postCreate";
	}
	
	@PostMapping("/{communityName}/createPost")
    public String createPost(@PathVariable("communityName") String communityName, @ModelAttribute PostDTO postDTO) {
		postService.createPost(communityName, postDTO);
        return "redirect:/community/" + URLEncoder.encode(communityName, StandardCharsets.UTF_8);
    }
	
	@GetMapping("/{communityName}/postDetail/{postId}")
    public String getPostDetail(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		List<ReplyDTO> replies =  replyService.getRepliesByPostId(postId);
		List<Map<String, String>> profileUUIDList = memberService.getProfileUUIDList();
		Map<String, String> profileUUIDMap = profileUUIDList.stream()
		        .collect(Collectors.toMap(
		            map -> map.get("NICKNAME"),
		            map -> map.get("PROFILE_UUID"),
		            (existing, replacement) -> existing
		        ));
		model.addAttribute("replies", replies);
		model.addAttribute("memberDTO", memberDTO);
		model.addAttribute("post", postService.findPostDetail(postId));
        model.addAttribute("communityName", communityName);
        model.addAttribute("profileUUIDMap", profileUUIDMap);
        return "post/postDetail";
    }
	
	@GetMapping("/{communityName}/updatePost/{postId}")
    public String updatePost(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		PostDTO post = postService.findPostDetail(postId);
		model.addAttribute("author", memberDTO.getNickname());
        model.addAttribute("post", post);
        model.addAttribute("communityName", communityName);
        return "post/postUpdate";
    }
	
	@PostMapping("/{communityName}/updatePost/{postId}")
    public String updatePost(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId, @ModelAttribute PostDTO postDTO) {
		postService.updatePost(postDTO);
        return "redirect:/community/" + URLEncoder.encode(communityName, StandardCharsets.UTF_8);
    }
	
	@PostMapping("/{communityName}/deletePost/{postId}")
	@ResponseBody
    public ResponseEntity<?> deletePost(@PathVariable("communityName") String communityName, @PathVariable("postId") long postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok().build();
    }
	
    @PostMapping("/{communityName}/postDetail/{postId}/createReply")
    public ResponseEntity<?> createReply(@PathVariable("communityName") String communityName,
    						  @PathVariable("postId") long postId,
    						  @RequestBody ReplyDTO replyDTO) {
        replyService.createReply(replyDTO, communityName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{communityName}/postDetail/{postId}/updateReply")
    public ResponseEntity<?> updateReply(@PathVariable("communityName") String communityName,
			  				  @PathVariable("postId") long postId,
			  				  @RequestBody ReplyDTO replyDTO) {
    	replyService.updateReply(replyDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{communityName}/postDetail/{postId}/deleteReply")
    public ResponseEntity<?> deleteReply(@PathVariable("communityName") String communityName,
							  @PathVariable("postId") long postId,
							  @RequestParam("replyId") Long replyId) {
        replyService.deleteReply(postId, replyId);
        return ResponseEntity.ok().build();
    }
	
}
