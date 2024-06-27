package com.application.mediquanta.pharmacy.controller;

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

import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.pharmacy.service.PharmacyService;
import com.application.mediquanta.util.SearchData;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {

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
        model.addAttribute("pharmacy", pharmacyDTO);
        model.addAttribute("editMode", false);
        return "pharmacy/pharmacyDetail";
    }
}
