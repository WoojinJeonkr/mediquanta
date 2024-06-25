package com.application.mediquanta.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// TODO : 1. 병원 정보를 가져오는 api를 사용해서 정보를 DB에 저장하는 코드 작성
	@GetMapping("/findList")
	@ResponseBody
	public void findHospitalList() {
		hospitalService.saveHospitalsToDatabase();
	}
	
	// TODO : 2. 병원 상세 목록 기능 개발
	
	// TODO : 3. 병원 정보 수정 기능 개발 (관리자인 경우)
	
	// TODO : 4. 병원 정보 삭제 기능 개발 (관리자인 경우)
	
}
