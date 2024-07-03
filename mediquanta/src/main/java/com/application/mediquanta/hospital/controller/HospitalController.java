package com.application.mediquanta.hospital.controller;

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
@RequestMapping("/hospital")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
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
	public List<HospitalDTO> getHospitalList() {
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
        List<PharmacyDTO> pharmacies = pharmacyService.selectNearestPharmacies(hospitalDTO.getLatitude(), hospitalDTO.getLongitude());
        model.addAttribute("hospital", hospitalDTO);
        model.addAttribute("pharmacies", pharmacies);
        return "hospital/hospitalDetail";
    }
	
	@GetMapping("/viewHospitalUpdate")
	public String viewHospitalUpdate(@RequestParam("hospitalId") long hospitalId, Model model) {
		HospitalDTO hospitalDTO = hospitalService.getHospitalDetails(hospitalId);
		model.addAttribute("hospital", hospitalDTO);
		return "hospital/hospitalUpdate";
	}
	
	@GetMapping("/findLoc")
	@ResponseBody
    public Map<String, Double> getLatitudeLongitude(@RequestParam("address") String address) {
        return hospitalService.kakaoLocalAPI(address);
    }
	
	@PostMapping("/updateHospInfo")
	public String updateHospInfo(@ModelAttribute HospitalDTO hospitalDTO) {
		hospitalService.updateHospInfo(hospitalDTO);
		return "redirect:/hospital";
	}
	
	@PostMapping("/deleteHospital")
	public ResponseEntity<?> deleteHospital(@RequestParam("hospitalId") long hospitalId) {
		hospitalService.deleteHospital(hospitalId);
		return ResponseEntity.ok().build();
	}
}
