package com.application.mediquanta.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.hospital.service.HospitalService;
import com.application.mediquanta.util.SearchData;

import jakarta.servlet.http.HttpSession;

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
	public String viewHospitalList(Model model, HttpSession session) {
		model.addAttribute("role", (String)session.getAttribute("role"));
		return "hospital/hospitalList";
	}
	
	@PostMapping("/getHospitalList")
	@ResponseBody
	public List<HospitalDTO> getHospitalList(Model model, HttpSession session) {
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
	
	@GetMapping("/details")
    public String viewHospitalDetails(@RequestParam("hospitalId") long hospitalId, Model model) {
        HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
        model.addAttribute("hospital", hospitalDTO);
        return "hospital/hospitalDetail";
    }
	
	// TODO : 1.hospitalUpdate.html script 부분 수정하기 - controller부터 mapper까지의 로직 제대로 작성했는지 확인하기
	@GetMapping("/viewHospitalUpdate")
	public String viewHospitalUpdate(@RequestParam("hospitalId") long hospitalId, Model model) {
		HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
		model.addAttribute("hospital", hospitalDTO);
		return "hospital/hospitalUpdate";
	}
	
	@PostMapping("/updateHospInfo")
	@ResponseBody
	public String updateHospInfo(@ModelAttribute HospitalDTO hospitalDTO) {
		hospitalService.udpateHospInfo(hospitalDTO);
		return "hospital/details/" + hospitalDTO.getHospitalId();
	}
	
	// TODO : 2. 병원 정보 삭제 기능 개발 (관리자인 경우)
	
}
