package com.application.mediquanta.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.dto.SearchData;
import com.application.mediquanta.hospital.service.HospitalService;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping("/saveList")
	@ResponseBody
	public void saveHospitalList() {
		hospitalService.saveHospitalsToDatabase();
	}
	
	@GetMapping
	public String viewHospitalList(Model model) {
		return "hospital/hospitalList";
	}
	
	@PostMapping("/findHospitalList")
	@ResponseBody
	public List<HospitalDTO> findHospitalList() {
		return hospitalService.getHospitalList();
	}
	
	@PostMapping("/searchHospitalByName")
	@ResponseBody
	public List<HospitalDTO> searchHospitalByName(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalByName(searchData.getName());
	}
	
	@PostMapping("/searchHospitalBySidoCdNm")
	@ResponseBody
	public List<HospitalDTO> searchHospitalBySidoCdNm(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalBySidoCdNm(searchData.getSidoCdNm());
	}
	
	@PostMapping("/searchHospitalByType")
	@ResponseBody
	public List<HospitalDTO> searchHospitalByType(@RequestBody SearchData searchData) {
		return hospitalService.searchHospitalByType(searchData.getType());
	}
	
	// TODO : 1. 병원 상세 목록 기능 개발
	@GetMapping("/details")
    public String viewHospitalDetails(@RequestParam("hospitalId") long hospitalId, Model model) {
        HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
        model.addAttribute("hospital", hospitalDTO);
        return "hospital/hospitalDetail";
    }
	
	// TODO : 2. 병원 정보 수정 기능 개발 (관리자인 경우)
	
	// TODO : 3. 병원 정보 삭제 기능 개발 (관리자인 경우)
	
}
