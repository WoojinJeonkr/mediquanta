package com.application.mediquanta.pharmacy.service;

import java.util.List;

import com.application.mediquanta.pharmacy.dto.PharmacyDTO;

public interface PharmacyService {

	public void savePharmacyListToDatabase();
	public List<PharmacyDTO> getPharmacyList();
	public List<PharmacyDTO> searchPharmacyByName(String name);
	public List<PharmacyDTO> searchPharmacyBySidoCdNm(String sidoCdNm);
	public PharmacyDTO getPharmacyDetails(long pharmacyId);
	public List<PharmacyDTO> selectNearestPharmacies(double latitude, double longitude);
	
}
