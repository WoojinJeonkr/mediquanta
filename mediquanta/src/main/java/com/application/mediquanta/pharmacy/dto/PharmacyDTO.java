package com.application.mediquanta.pharmacy.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PharmacyDTO {

	private Long pharmacyId;
	private String pharmacyName;
	private String type;
	private String sidoCdNm;
	private String sgguCdNm;
	private String phone;
	private double latitude;
	private double longitude;
	private String address;
	private Date createdAt;
	private Date updatedAt;
	
}
