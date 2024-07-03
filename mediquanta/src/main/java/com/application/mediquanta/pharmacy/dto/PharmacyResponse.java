package com.application.mediquanta.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class PharmacyResponse {

	private String pharmacyName;
	private String type;
	private String sidoCdNm;
	private String sgguCdNm;
	private String phone;
	private double latitude;
	private double longitude;
	private String address;
	
}
