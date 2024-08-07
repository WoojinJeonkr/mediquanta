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
import com.application.mediquanta.error.BadRequestError;
import com.application.mediquanta.error.NotFoundError;
import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.service.MemberService;
import com.application.mediquanta.post.dto.PostDTO;
import com.application.mediquanta.post.service.PostService;
import com.application.mediquanta.reply.dto.ReplyDTO;
import com.application.mediquanta.reply.service.ReplyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
@Tag(name="Community", description="커뮤니티 Api")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
    private ReplyService replyService;

	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 라운지 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>커뮤니티 라운지 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 커뮤니티", description="커뮤니티 라운지 페이지로 이동")
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
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 생성 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>커뮤니티 생성 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 커뮤니티 생성", description="새로운 커뮤니티 정보 입력 페이지로 이동")
	@GetMapping("/createCommunity")
	public String createCommunity() {
		return "community/communityCreate";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 생성 여부가 true, false로 반환됩니다.",
			content = @Content(
		        mediaType = "application/json",
		        schema = @Schema(type = "boolean")
		    )
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="커뮤니티 존재 여부 확인", description="커뮤니티 이름을 통해 DB에 이름이 존재하는지 여부를 반환")
	@PostMapping("/validCommunityName")
	@ResponseBody
	public String validCommunityName(@RequestParam("communityName") String communityName) {
		return communityService.validCommunityName(communityName);
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 생성 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=CommunityDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="커뮤니티 생성", description="입력한 정보를 기반으로 새로운 커뮤니티 생성을 관리자에게 요청합니다")
	@PostMapping("/createCommunity")
	public String createCommunity(@ModelAttribute CommunityDTO communityDTO) {
		communityService.createCommunity(communityDTO);
		return "redirect:/community";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 승인 성공"
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="커뮤니티 승인", description="생성 요청한 커뮤니티를 커뮤니티 관리 페이지에서 확인 후 관리자가 승인합니다")
	@PostMapping("/agreeCommunity")
	@ResponseBody
	public void agreeCommunity(@RequestParam("communityId") long communityId) {
		communityService.agreeCommunity(communityId);
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "커뮤니티 상세 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>커뮤니티 상세 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 커뮤니티 상세", description="커뮤니티 내 게시글 목록 출력")
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
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 작성 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>게시글 작성 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 게시글 작성", description="게시글 작성 페이지로 이동합니다.")
	@GetMapping("/{communityName}/createPost")
	public String createPost(@PathVariable("communityName") String communityName, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		model.addAttribute("author", memberDTO.getNickname());
		model.addAttribute("communityName", communityName);
		return "post/postCreate";
	}
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 작성 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=PostDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="게시글 작성", description="게시글 작성 페이지에서 게시글 작성 후 커뮤니티 목록으로 이동합니다.")
	@PostMapping("/{communityName}/createPost")
    public String createPost(@PathVariable("communityName") String communityName, 
    			  			 @ModelAttribute PostDTO postDTO) {
		postService.createPost(communityName, postDTO);
        return "redirect:/community/" + URLEncoder.encode(communityName, StandardCharsets.UTF_8);
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 상세 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>게시글 상세 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 게시글 상세", description="게시글 상세 페이지로 이동합니다.")
	@GetMapping("/{communityName}/postDetail/{postId}")
    public String getPostDetail(@PathVariable("communityName") String communityName, 
    			@PathVariable("postId") long postId, Model model, HttpSession session) {
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
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 수정 페이지 이동 성공",
			content = @Content(
				mediaType = "text/html",
	            schema = @Schema(
	                type = "string",
	                example = "<html><body><h1>게시글 수정 페이지로 성공적으로 이동했습니다.</h1></body></html>"
	            )
			)
		),
		@ApiResponse(
		    responseCode = "404",
		    description = "페이지를 찾을 수 없음",
		    content = @Content(
		        schema = @Schema(
		            implementation = NotFoundError.class
		        ),
		        examples = @ExampleObject(
		            name = "Not Found",
		            value = "{ \"message\": \"요청한 리소스를 찾을 수 없습니다\", \"errorCode\": \"404\" }"
		        )
		    )
		)
	})
	@Operation(summary="페이지 이동 - 게시글 수정", description="게시글 수정 페이지로 이동합니다.")
	@GetMapping("/{communityName}/updatePost/{postId}")
    public String updatePost(@PathVariable("communityName") String communityName,
    						 @PathVariable("postId") long postId, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("memberId");
		MemberDTO memberDTO = memberService.getUserInfo(memberId);
		PostDTO post = postService.findPostDetail(postId);
		model.addAttribute("author", memberDTO.getNickname());
        model.addAttribute("post", post);
        model.addAttribute("communityName", communityName);
        return "post/postUpdate";
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 수정 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=PostDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="게시글 수정", description="게시글 수정 페이지에서 게시글 수정 후 게시글 상세 화면으로 이동합니다.")
	@PostMapping("/{communityName}/updatePost/{postId}")
    public String updatePost(@PathVariable("communityName") String communityName,
    						 @PathVariable("postId") long postId, @ModelAttribute PostDTO postDTO) {
		postService.updatePost(postDTO);
        return "redirect:/community/" + URLEncoder.encode(communityName, StandardCharsets.UTF_8) + 
        		"/postDetail/" + String.valueOf(postId);
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "게시글 삭제 성공"
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="게시글 삭제", description="게시글 상세 페이지에서 작성자만 게시글을 삭제합니다.")
	@PostMapping("/{communityName}/deletePost/{postId}")
	@ResponseBody
    public ResponseEntity<?> deletePost(@PathVariable("communityName") String communityName,
							 @PathVariable("postId") long postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok().build();
    }
	
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "댓글 작성 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=ReplyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="댓글 작성", description="게시글 상세 페이지에서 댓글을 작성합니다.")
	@PostMapping("/{communityName}/postDetail/{postId}/createReply")
    public ResponseEntity<?> createReply(@PathVariable("communityName") String communityName,
    						  @PathVariable("postId") long postId,
    						  @RequestBody ReplyDTO replyDTO) {
        replyService.createReply(replyDTO, communityName);
        return ResponseEntity.ok().build();
    }

	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "댓글 수정 성공",
			content = @Content(
	            schema = @Schema(
	                implementation=ReplyDTO.class
	            )
			)
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="댓글 수정", description="게시글 상세 페이지에서 댓글을 수정합니다.")
	@PostMapping("/{communityName}/postDetail/{postId}/updateReply")
    public ResponseEntity<?> updateReply(@PathVariable("communityName") String communityName,
    						  @PathVariable("postId") long postId,
			  				  @RequestBody ReplyDTO replyDTO) {
    	replyService.updateReply(replyDTO);
        return ResponseEntity.ok().build();
    }

	@ApiResponses(value= {
		@ApiResponse(responseCode = "200",
			description = "댓글 삭제 성공"
		),
		@ApiResponse(
		    responseCode = "400",
		    description = "잘못된 접근",
		    content = @Content(
		        schema = @Schema(
		            implementation = BadRequestError.class
		        ),
		        examples = @ExampleObject(
		            name = "Bad Request",
		            value = "{ \"message\": \"잘못된 요청입니다\", \"errorCode\": \"400\" }"
		        )
		    )
		)
	})
	@Operation(summary="댓글 삭제", description="게시글 상세 페이지에서 댓글을 삭제합니다.")
	@PostMapping("/{communityName}/postDetail/{postId}/deleteReply")
    public ResponseEntity<?> deleteReply(@PathVariable("communityName") String communityName,
    						  @PathVariable("postId") long postId,
							  @RequestParam("replyId") Long replyId) {
        replyService.deleteReply(postId, replyId);
        return ResponseEntity.ok().build();
    }
	
}
