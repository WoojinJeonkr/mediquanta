package com.application.mediquanta.hospital.service;

import java.util.List;

import com.application.mediquanta.hospital.dto.HospitalDTO;

public interface HospitalService {
	public void saveHospitalsToDatabase();
	public List<HospitalDTO> getHospitalList();
	public List<HospitalDTO> searchHospitalByName(String name);
	public List<HospitalDTO> searchHospitalBySidoCdNm(String sidoCdNm);
	public List<HospitalDTO> searchHospitalByType(String type);
	public HospitalDTO getHospitalDetails(long hospitalId);
}
