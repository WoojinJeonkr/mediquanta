package com.application.mediquanta.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping("/findList")
	@ResponseBody
	public void findHospitalList() {
		hospitalService.saveHospitalsToDatabase();
	}
	
	@GetMapping
	public String viewHospitalList(Model model) {
		List<HospitalDTO> hospitalList = hospitalService.getHospitalList();
		model.addAttribute("hospitalList", hospitalList);
		return "hospital/hospitalList";
	}
	
	// TODO : 1. 변경된 데이터로 병원 목록 표시하기 (hospitalList.html)
	@PostMapping("/searchHospitalByName")
	@ResponseBody
	public HospitalDTO searchHospitalByName(@RequestParam("name") String name) {
		return hospitalService.searchHospitalByName(name);
	}
	
	@PostMapping("/searchHospitalBySidoCdNm")
	@ResponseBody
	public List<HospitalDTO> searchHospitalBySidoCdNm(@RequestParam("sidoCdNm") String sidoCdNm) {
		return hospitalService.searchHospitalBySidoCdNm(sidoCdNm);
	}
	
	@PostMapping("/searchHospitalByType")
	@ResponseBody
	public List<HospitalDTO> searchHospitalByType(@RequestParam("type") String type) {
		return hospitalService.searchHospitalByType(type);
	}
	
	// TODO : 2. 병원 상세 목록 기능 개발
	
	// TODO : 3. 병원 정보 수정 기능 개발 (관리자인 경우)
	
	// TODO : 4. 병원 정보 삭제 기능 개발 (관리자인 경우)
	
}
