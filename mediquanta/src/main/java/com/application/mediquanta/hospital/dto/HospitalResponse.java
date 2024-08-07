package com.application.mediquanta.hospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(title="병원 Api 응답 DTO")
public class HospitalResponse {

	@Schema(description = "병원 이름", example = "삼성서울병원")
	private String hospitalName;
	@Schema(description = "병원 유형", example = "상급종합")
	private String type;
	@Schema(description = "시도 코드 명", example = "서울")
	private String sidoCdNm;
	@Schema(description = "동구 코드 명", example = "강남구")
	private String sgguCdNm;
	@Schema(description = "병원 주소", example = "서울특별시 강남구 일원로 81, (일원동, 삼성의료원)")
	private String address;
	@Schema(description = "병원 전화번호", example = "02-3410-2114")
	private String phone;
	@Schema(description = "병원 위도", example = "37.4882977")
	private double latitude;
	@Schema(description = "병원 경도", example = "127.0851508")
	private double longitude;
	@Schema(description = "병원 홈페이지 주소", example = "http://www.samsunghospital.com/home/main/index.do")
	private String hospitalUrl;
	
}
