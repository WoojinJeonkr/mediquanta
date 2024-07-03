package com.application.mediquanta.hospital.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	private double latitude;
	private double longitude;
	private String hospitalUrl;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
	
}
