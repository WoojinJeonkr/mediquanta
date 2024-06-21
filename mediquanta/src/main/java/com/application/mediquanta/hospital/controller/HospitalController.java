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
	
	// TODO : 6. 병원 정보를 가져오는 api를 사용해서 정보를 가져오는 코드 작성
	
	@PostMapping("/hospitalList")
	public List<HospitalDTO> getHospitalList() {
		return hospitalService.getHospitalList();
	}
	
	// TODO : 7. 병원 상세 목록 기능 개발
	
	// TODO : 8. 병원 정보 수정 기능 개발 (관리자인 경우)
	
	// TODO : 9. 병원 정보 삭제 기능 개발 (관리자인 경우)
	
}
