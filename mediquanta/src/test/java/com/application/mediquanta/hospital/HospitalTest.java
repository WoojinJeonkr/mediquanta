package com.application.mediquanta.hospital;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HospitalTest {

	/*
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private HospitalBookmarkService hospitalBookmarkService;
	
	@Order(1)
	@DisplayName("전체 병원 목록 조회")
	@Test
	public void getHospitalList() {
		System.out.println("전체 병원 데이터 개수 : " + hospitalService.getHospitalList().size());
	}
	
	@Order(2)
	@DisplayName("병원 이름으로 병원 조회")
	@Test
	public void searchHospitalByName() {
		System.out.println(hospitalService.searchHospitalByName("삼성서울병원"));
	}
	
	@Order(3)
	@DisplayName("지역으로 병원 조회")
	@Test
	public void searchHospitalBySidoCdNm() {
		System.out.println("서울 지역 병원 데이터 개수 : " + hospitalService.searchHospitalBySidoCdNm("서울").size());
	}
	
	@Order(4)
	@DisplayName("병원 유형으로 병원 조회")
	@Test
	public void searchHospitalByType() {
		System.out.println("상급종합 병원 데이터 개수 : " + hospitalService.searchHospitalByType("상급종합").size());
	}
	
	@Order(5)
	@DisplayName("병원 상세 조회")
	@Test
	public void getHospitalDetails() {
		System.out.println(hospitalService.getHospitalDetails(1));
	}
	
	@Order(6)
	@DisplayName("근처 병원 조회")
	@Test
	public void selectNearestHospitals() {
		System.out.println(hospitalService.selectNearestHospitals(37.52, 127.05));
	}
	
	@Order(7)
	@DisplayName("병원 유형 현황 조회")
	@Test
	public void getHospitalTypeCounts() {
		System.out.println(hospitalService.getHospitalTypeCounts());
	}
	
	@Order(8)
	@DisplayName("병원 정보 수정")
	@Test
	public void updateHospInfo() {
		HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(1);
		hospitalDTO.setHospitalUrl("http://www.samsunghospital.com/home/main/index.do");
		hospitalService.updateHospInfo(hospitalDTO);
		System.out.println(hospitalService.getHospitalDetails(1));
	}
	
	@Order(9)
	@DisplayName("병원 정보 삭제") // 테스트 시 기존 데이터를 삭제하지 않고 기존 데이터 중 하나를 복제하여 테스트
	@Test
	public void deleteHospital() {
		hospitalService.deleteHospital(6615);
	}
	
	@Order(10)
	@DisplayName("병원 북마크 추가 및 조회")
	@Test
	public void addHospitalBookmark() {
		hospitalBookmarkService.addHospitalBookmark("test2", 1);
		System.out.println(hospitalBookmarkService.getBookmarksForMember("test2"));
	}
	
	@Order(11)
	@DisplayName("병원 북마크 제거")
	@Test
	public void removeBookmarksForMember() {
		hospitalBookmarkService.removeHospitalBookmark("test2", 1);
		System.out.println(hospitalBookmarkService.getBookmarksForMember("test2"));
	}
	*/
}
