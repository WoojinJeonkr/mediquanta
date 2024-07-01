package com.application.mediquanta.hospital.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.hospital.dto.HospitalDTO;

@Mapper
public interface HospitalDAO {
	public void saveHospital(HospitalDTO hospitalDTO);
	public List<HospitalDTO> getHospitalList();
	public HospitalDTO searchHospitalByName(String name);
	public List<HospitalDTO> searchHospitalBySidoCdNm(String sidoCdNm);
	public List<HospitalDTO> searchHospitalByType(String type);
	public HospitalDTO getHospitalDetails(long hospitalId);
	public List<HospitalDTO> selectNearestHospitals(Map<String, Object> params);
	public List<Map<String, Object>> getHospitalTypeCounts();
	public void updateHospInfo(HospitalDTO hospitalDTO);
	public void deleteHospital(long hospitalId);
}
