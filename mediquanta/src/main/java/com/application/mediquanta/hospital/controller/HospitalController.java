package com.application.mediquanta.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;

@Controller
@RequestMapping("/hospital")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping
	public String viewHospitalList() {
		return "hospital/hospitalList";
	}
	
	@PostMapping("/hospitalList")
	public List<HospitalDTO> getHospitalList() {
		return hospitalService.getHospitalList();
	}
	
}
