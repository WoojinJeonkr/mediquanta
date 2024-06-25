package com.application.mediquanta.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HospitalApiDTO {

	private String hospitalName;
	private String type;
	private String sidoCdNm;
	private String sgguCdNm;
	private String address;
	private String phone;
	private double latitude;
	private double longitude;
	private String hospitalUrl;
	
	public HospitalResponse toResponse() {
        return HospitalResponse.builder()
                .hospitalName(hospitalName)
                .type(type)
                .sidoCdNm(sidoCdNm)
                .sgguCdNm(sgguCdNm)
                .address(address)
                .phone(phone)
                .latitude(latitude)
                .longitude(longitude)
                .hospitalUrl(hospitalUrl)
                .build();
    }
	
}
