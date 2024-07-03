package com.application.mediquanta.address.dto;

import lombok.Data;

@Data
public class Documents {

	private LocAddress address;
	private String address_name;
	private String address_type;
	private RoadAddress roadAddress;
	private double x;
	private double y;
	
}
