package com.application.mediquanta.community;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommunityTest {

	/*
	@Autowired
	private CommunityService communityService;
	
	@Order(1)
	@DisplayName("커뮤니티 이름 중복 확인")
	@Test
	public void validCommunityName() {
		System.out.println(communityService.validCommunityName("공지사항").equals("y") ? "사용 가능" : "사용 불가");
	}
	
	@Order(2)
	@DisplayName("커뮤니티 생성")
	@Test
	public void createCommunity() {
		CommunityDTO communityDTO = new CommunityDTO();
		communityDTO.setCommunityName("공지사항");
		communityDTO.setDescription("공지사항 게시판입니다.");
		communityDTO.setPurpose("공지사항 작성 게시판");
		communityDTO.setCreatedAt(new Date());
		communityService.createCommunity(communityDTO);
	}
	
	@Order(3)
	@DisplayName("커뮤니티 승인")
	@Test
	public void agreeCommunity() {
		communityService.agreeCommunity(1);
	}
	
	@Order(4)
	@DisplayName("전체 승인된 커뮤니티 목록 조회")
	@Test
	public void getCommunityList() {
		System.out.println(communityService.getCommunityList());
	}
	
	@Order(5)
	@DisplayName("커뮤니티 조회")
	@Test
	public void findCommunity() {
		communityService.updateViewCnt("공지사항");
		System.out.println(communityService.findCommunity(1));
	}
	
	@Order(6)
	@DisplayName("이름으로 커뮤니티 조회")
	@Test
	public void findCommunityByName() {
		communityService.updateViewCnt("공지사항");
		System.out.println(communityService.findCommunityByCommunityName("공지사항"));
	}
	*/
}
