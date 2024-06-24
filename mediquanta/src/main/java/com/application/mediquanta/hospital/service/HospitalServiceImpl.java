package com.application.mediquanta.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.application.mediquanta.hospital.dao.HospitalDAO;

@Service
public class HospitalServiceImpl implements HospitalService {
	
	@Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.baseUrl}")
    private String baseUrl;

	@Autowired
	private HospitalDAO hospitalDAO;
	

}
