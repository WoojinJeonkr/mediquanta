package com.application.mediquanta.pharmacy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.pharmacy.service.PharmacyService;
import com.application.mediquanta.util.SearchData;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping("/saveList")
	@ResponseBody
	public void savePharmacyList() {
		pharmacyService.savePharmacyListToDatabase();
	}
	
	@GetMapping
	public String viewPharmacyList(Model model, HttpSession session) {
		model.addAttribute("role", (String) session.getAttribute("role"));
		return "pharmacy/pharmacyList";
	}
	
	@PostMapping("/getPharmacyList")
	@ResponseBody
	public List<PharmacyDTO> getPharmacyList() {
		return pharmacyService.getPharmacyList();
	}
	
	@PostMapping("/searchPharmacyByName")
	@ResponseBody
	public List<PharmacyDTO> searchPharmacyByName(@RequestBody SearchData searchData) {
		return pharmacyService.searchPharmacyByName(searchData.getName());
	}
	
	@PostMapping("/searchPharmacyBySidoCdNm")
	@ResponseBody
	public List<PharmacyDTO> searchPharmacyBySidoCdNm(@RequestBody SearchData searchData) {
		return pharmacyService.searchPharmacyBySidoCdNm(searchData.getSidoCdNm());
	}
	
	@GetMapping("/details")
    public String viewPharmacyDetails(@RequestParam("pharmacyId") long pharmacyId, Model model) {
		PharmacyDTO pharmacyDTO = pharmacyService.getPharmacyDetails(pharmacyId);
		List<HospitalDTO> hospitals = hospitalService.selectNearestHospitals(pharmacyDTO.getLatitude(), pharmacyDTO.getLongitude());
        model.addAttribute("pharmacy", pharmacyDTO);
        model.addAttribute("hospitals", hospitals);
        return "pharmacy/pharmacyDetail";
    }
	
	// TODO : 2. 약국 정보 수정 기능 개발 (관리자인 경우)
	@GetMapping("/viewPharmacyUpdate")
	public String viewPharmacyUpdate(@RequestParam("pharmacyId") long pharmacyId, Model model) {
		PharmacyDTO pharmacyDTO = pharmacyService.getPharmacyDetails(pharmacyId);
		model.addAttribute("pharmacy", pharmacyDTO);
		return "pharmacy/pharmacyUpdate";
	}
	
	@GetMapping("/findLoc")
	@ResponseBody
    public Map<String, Double> getLatitudeLongitude(@RequestParam("address") String address) {
        return pharmacyService.kakaoLocalAPI(address);
    }
	
	@PostMapping("/updatePharmacyInfo")
	public String updatePharmacyInfo(@ModelAttribute PharmacyDTO pharmacyDTO) {
		pharmacyService.updatePharmacyInfo(pharmacyDTO);
		return "redirect:/pharmacy";
	}
	
	@PostMapping("/deletePharmacy")
	public ResponseEntity<?> deletePharmacy(@RequestParam("pharmacyId") long pharmacyId) {
		pharmacyService.deletePharmacy(pharmacyId);
		return ResponseEntity.ok().build();
	}
}
