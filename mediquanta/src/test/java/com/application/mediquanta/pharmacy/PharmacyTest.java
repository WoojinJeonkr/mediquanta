package com.application.mediquanta.pharmacy;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PharmacyTest {

	/*
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PharmacyBookmarkService pharmacyBookmarkService;
	
	@Order(1)
	@DisplayName("전체 약국 목록 조회")
	@Test
	public void getHospitalList() {
		System.out.println("전체 약국 데이터 개수 : " + pharmacyService.getPharmacyList().size());
	}
	
	@Order(2)
	@DisplayName("약국 이름으로 약국 조회")
	@Test
	public void searchHospitalByName() {
		System.out.println(pharmacyService.searchPharmacyByName("청담장수 한약국"));
	}
	
	@Order(3)
	@DisplayName("지역으로 약국 조회")
	@Test
	public void searchHospitalBySidoCdNm() {
		System.out.println("서울 지역 약국 데이터 개수 : " + pharmacyService.searchPharmacyBySidoCdNm("서울").size());
	}
	
	@Order(4)
	@DisplayName("약국 상세 조회")
	@Test
	public void getHospitalDetails() {
		System.out.println(pharmacyService.getPharmacyDetails(1));
	}
	
	@Order(5)
	@DisplayName("근처 약국 조회")
	@Test
	public void selectNearestHospitals() {
		System.out.println(pharmacyService.selectNearestPharmacies(37.52, 127.05));
	}
	
	@Order(6)
	@DisplayName("약국 유형 현황 조회")
	@Test
	public void getHospitalTypeCounts() {
		System.out.println(pharmacyService.getPharmacyTypeCounts());
	}
	
	@Order(7)
	@DisplayName("약국 정보 수정")
	@Test
	public void updateHospInfo() {
		PharmacyDTO pharmacyDTO = pharmacyService.getPharmacyDetails(1);
		pharmacyDTO.setPhone("02-542-9828");
		pharmacyService.updatePharmacyInfo(pharmacyDTO);
		System.out.println(pharmacyService.getPharmacyDetails(1));
	}
	
	@Order(8)
	@DisplayName("약국 정보 삭제") // 테스트 시 기존 데이터를 삭제하지 않고 기존 데이터 중 하나를 복제하여 테스트
	@Test
	public void deleteHospital() {
		pharmacyService.deletePharmacy(18621);
	}
	
	@Order(9)
	@DisplayName("약국 북마크 추가 및 조회")
	@Test
	public void addHospitalBookmark() {
		pharmacyBookmarkService.addPharmacyBookmark("test2", 1);
		System.out.println(pharmacyBookmarkService.getBookmarksForMember("test2"));
	}
	
	@Order(10)
	@DisplayName("약국 북마크 제거")
	@Test
	public void removeBookmarksForMember() {
		pharmacyBookmarkService.removePharmacyBookmark("test2", 1);
		System.out.println(pharmacyBookmarkService.getBookmarksForMember("test2"));
	}
	*/
}
