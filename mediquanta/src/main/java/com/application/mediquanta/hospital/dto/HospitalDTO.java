package com.application.mediquanta.hospital.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HospitalDTO {

	private Long hospitalId;
	private String hospitalName;
	private String type;
	private String sidoCdNm;
	private String sgguCdNm;
	private String address;
	private String phone;
	private Date createdAt;
	private Date updatedAt;
	
}
