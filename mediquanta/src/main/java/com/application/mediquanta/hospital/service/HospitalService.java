package com.application.mediquanta.hospital.service;

import java.util.List;
import java.util.Map;

import com.application.mediquanta.hospital.dto.HospitalDTO;

public interface HospitalService {
	public void saveHospitalsToDatabase();
	public List<HospitalDTO> getHospitalList();
	public List<HospitalDTO> searchHospitalByName(String name);
	public List<HospitalDTO> searchHospitalBySidoCdNm(String sidoCdNm);
	public List<HospitalDTO> searchHospitalByType(String type);
	public HospitalDTO getHospitalDetails(long hospitalId);
	public Map<String, Double> kakaoLocalAPI(String query);
	public List<HospitalDTO> selectNearestHospitals(double latitude, double longitude);
	public List<Map<String, Object>> getHospitalTypeCounts();
	public void udpateHospInfo(HospitalDTO hospitalDTO);
}
