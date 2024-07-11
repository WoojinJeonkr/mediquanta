package com.application.mediquanta.member;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {

	/*
	@Autowired
	private WebApplicationContext webApplicationContext;
	
    private MockMvc mockMvc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;

	@BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Disabled()
	@Test
    public void testUploadImageFile() throws Exception {
        byte[] imageBytes = "fake image content".getBytes();
        MockMultipartFile imageFile = new MockMultipartFile("file", "test-image.png", "image/png", imageBytes);

        mockMvc.perform(multipart("/upload").file(imageFile))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "파일 업로드 성공: uploads/test-image.png"));
    }
	
	@Order(1)
    @DisplayName("회원가입")
    @Test
    public void createMember() throws ParseException, IllegalStateException, IOException {
        MemberDTO memberDTO = new MemberDTO();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = formatter.parse("1997-10-05");
        Date createdAt = formatter.parse("2024-06-06 23:49:12");
        Date lastLogin = formatter.parse("2024-06-06 23:49:12");

        if (memberService.checkValidId("test2").equals("y") && memberService.checkValidNickname("test2").equals("y")
        		&& memberService.checkValidEmail("test2@gmail.com").equals("y")) {
        	
        	memberDTO.setMemberId("test2");
            memberDTO.setPasswd("test2");
            memberDTO.setNickname("test2");
            memberDTO.setEmail("test2@gmail.com");
            memberDTO.setBirth(birth);
            memberDTO.setGender("F");
            memberDTO.setRole("USER");
            memberDTO.setZipcode("07021");
            memberDTO.setRoadAddress("서울 동작구 남부순환로 2003 (사당동)");
            memberDTO.setLandAddress("서울 동작구 사당동 1052-4");
            memberDTO.setEtcAddress(null);
            memberDTO.setCreatedAt(createdAt);
            memberDTO.setUpdatedAt(null);
            memberDTO.setLastLogin(lastLogin);
            
            memberDTO.setProfileOriginalName("test-image.png");
            memberDTO.setProfileUUID(UUID.randomUUID().toString());
            
            byte[] imageBytes = "fake image content".getBytes();
            MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", imageBytes);
            
            memberService.createMember(profileImage, memberDTO);
        } else {
            System.out.println("이미 회원 정보가 존재합니다.");
        }
    }
    
	@Order(2)
    @DisplayName("로그인")
	@Test
	public void login() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId("test2");

		 * [BCryptPasswordEncoder encode 비교]
		 * 
		 * BCryptPasswordEncoder를 사용하여 패스워드를 암호화할 때마다 생성되는 해시 값은 무작위하고 salt라 불리는 추가적인 비밀
		 * 값을 사용합니다. 이 salt는 매번 다르게 생성되기 때문에 같은 입력 값에 대해 항상 같은 해시가 생성되지 않습니다.
		 * 
		 * 만약 memberDTO.setPasswd(passwordEncoder.encode("test"));로 설정하고
		 * System.out.println(passwordEncoder.encode("test").equals(memberDTO.getPasswd(
		 * ))); 결과를 확인한다면 passwordEncoder.encode("test")를 호출할 때마다 새로운 salt와 함께 생성된 해시 값이
		 * 반환되므로, equals() 메서드를 통해 비교하면 항상 false가 반환됩니다.
		 * 
		 * 또한 만약 기존에 저장된 암호화된 패스워드인 encodedPassword와 비교하려는 새로운 비밀번호 "test"를
		 * passwordEncoder.encode("test")로 암호화한 값과 비교하고자 한다면, 비교가 항상 false가 됩니다.
		 * 
		 * 따라서 BCryptPasswordEncoder를 통한 password를 비교할 때는 matches() 메서드를 사용해야 합니다.

		memberDTO.setPasswd("test2");
		String resultMsg;
		if (memberDTO.getMemberId().equals("test2")
				&& passwordEncoder.matches(memberDTO.getPasswd(), passwordEncoder.encode("test2"))) {
			resultMsg = "로그인 성공";
		} else {
			resultMsg = "로그인 실패";
		}
		System.out.println(resultMsg);
	}
	
	@Order(3)
	@DisplayName("권한 확인")
	@Test
	public void checkRole() {
		System.out.printf("3. 권한 확인 : %s", memberService.checkRole("test2").equals("ADMIN") ? "권한이 있습니다.\n" : "권한이 없습니다.\n");
	}

	@Order(4)
	@DisplayName("회원 정보 조회")
	@Test
	public void getUserInfo() {
		System.out.println(memberService.getUserInfo("test2"));
	}
	
	@Order(5)
	@DisplayName("회원 정보 수정")
	@Test
	public void updateMember() throws ParseException, IllegalStateException, IOException {
		MemberDTO memberDTO = memberService.getUserInfo("test2");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = formatter.parse("1995-12-15");
        Date updatedAt = formatter.parse("2024-07-06 23:49:12");
        Date lastLogin = formatter.parse("2024-07-06 23:49:12");
		
		memberDTO.setMemberId("test2");
        memberDTO.setPasswd("test2");
        memberDTO.setNickname("test2");
        memberDTO.setEmail("test2@gmail.com");
        memberDTO.setBirth(birth);
        memberDTO.setGender("F");
        memberDTO.setRole("USER");
        memberDTO.setZipcode("07021");
        memberDTO.setRoadAddress("서울 동작구 남부순환로 2003 (사당동)");
        memberDTO.setLandAddress("서울 동작구 사당동 1052-4");
        memberDTO.setEtcAddress(null);
        memberDTO.setUpdatedAt(updatedAt);
        memberDTO.setLastLogin(lastLogin);
        
        memberDTO.setProfileOriginalName("test-image.png");
        memberDTO.setProfileUUID(UUID.randomUUID().toString());
        
        byte[] imageBytes = "fake image content".getBytes();
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", imageBytes);
        
        memberService.updateMember(profileImage, memberDTO);
	}
	
	@Order(6)
	@DisplayName("회원 탈퇴")
	@Test
	public void signOut() {
		memberService.signOut("test2");
	}
	
	@Order(7)
	@DisplayName("전체 회원 조회")
	@Test
	public void getMemberList() {
		System.out.println(memberService.getMemberList());
	}
	
	@Order(8)
	@DisplayName("전체 회원 프로필 목록 조회")
	@Test
	public void getProfileUUIDList() {
		System.out.println(memberService.getProfileUUIDList());
	}
	
	@Order(9)
	@DisplayName("전체 회원 권한 현황 조회")
	@Test
	public void getRoleCount() {
		System.out.println(memberService.getRoleCount());
	}
	
	@Order(10)
	@DisplayName("전체 회원 성별 현황 조회")
	@Test
	public void getGenderCount() {
		System.out.println(memberService.getGenderCount());
	}
	
	@Order(11)
	@DisplayName("전체 회원 탈퇴 현황 조회")
	@Test
	public void getActiveCount() {
		System.out.println(memberService.getActiveCount());
	}
	
	@Order(12)
	@DisplayName("회원 삭제")
	@Test
	public void deleteMember() throws ParseException, IllegalStateException, IOException {
		MemberDTO memberDTO = memberService.getUserInfo("test2");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date lastLogin = formatter.parse("2026-07-06 23:49:12");
		memberDTO.setLastLogin(lastLogin);
		byte[] imageBytes = "fake image content".getBytes();
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", imageBytes);
		memberService.updateMember(profileImage, memberDTO);
		
		memberService.deleteMember();
		System.out.println(memberDTO);
	}
	*/
}
