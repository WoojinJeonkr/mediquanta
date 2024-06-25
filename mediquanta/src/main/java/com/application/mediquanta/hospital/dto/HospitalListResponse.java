package com.application.mediquanta.hospital.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalListResponse {

	private List<HospitalResponse> hospitals;
	
}
