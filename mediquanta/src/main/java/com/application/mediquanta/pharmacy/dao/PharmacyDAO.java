package com.application.mediquanta.pharmacy.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.pharmacy.dto.PharmacyDTO;

@Mapper
public interface PharmacyDAO {

	public void savePharmacy(PharmacyDTO pharmacyDTO);
	public List<PharmacyDTO> getPharmacyList();
	public PharmacyDTO searchPharmacyByName(String name);
	public List<PharmacyDTO> searchPharmacyBySidoCdNm(String sidoCdNm);
	public PharmacyDTO getPharmacyDetails(long pharmacyId);
	public List<PharmacyDTO> selectNearestPharmacies(Map<String, Object> params);
	public List<Map<String, Object>> getPharmacyTypeCounts();
	public void updatePharmacyInfo(PharmacyDTO pharmacyDTO);
	public void deletePharmacy(long pharmacyId);
}
