package com.application.mediquanta.hospital.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title="병원 Api 응답 목록 DTO")
public class HospitalListResponse {

	@Schema(description="병원 목록")
	private List<HospitalResponse> hospitals;
	
}
