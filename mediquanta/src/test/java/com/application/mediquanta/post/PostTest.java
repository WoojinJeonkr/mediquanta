package com.application.mediquanta.post;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTest {

	/*
	@Autowired
	private PostService postService;

	@Order(1)
	@DisplayName("게시글 작성")
	@Test
	public void createPost() {
		for (int i = 1; i < 5; i++) {
			PostDTO postDTO = new PostDTO();
			postDTO.setTitle("공지사항" + i);
			postDTO.setAuthor("관리자");
			postDTO.setContent("테스트입니다.");
			postDTO.setViewCnt(0);
			postDTO.setCreatedAt(new Date());
			postService.createPost("공지사항", postDTO);
		}
	}
	
	@Order(2)
	@DisplayName("전체 게시글 조회")
	@Test
	public void getPostList() {
		System.out.println(postService.getPostList());
	}

	@Order(3)
	@DisplayName("최근 게시글 3개 조회")
	@Test
	public void getRecentPostList() {
		System.out.println(postService.getRecentPostList());
	}
	
	@Order(4)
	@DisplayName("전체 게시글 수 조회")
	@Test
	public void getPostListCount() {
		System.out.println(postService.getPostListCount("공지사항"));
	}
	
	@Order(5)
	@DisplayName("게시글 상세 조회")
	@Test
	public void getPostDetail() {
		System.out.println(postService.findPostDetail(1));
	}
	
	@Order(6)
	@DisplayName("게시글 수정")
	@Test
	public void updatePost() {
		long size = postService.getPostListCount("공지사항");
		PostDTO postDTO = postService.findPostDetail(size);
		postDTO.setContent("공지사항 테스트입니다.");
		postDTO.setUpdatedAt(new Date());
		postService.updatePost(postDTO);
		System.out.println(postService.findPostDetail(size));
	}
	
	@Order(7)
	@DisplayName("게시글 삭제")
	@Test
	public void deletePost() {
		long size = postService.getPostListCount("공지사항");
		postService.deletePost(size);
		System.out.println(postService.getPostListCount("공지사항"));
	}
	*/
	
}
