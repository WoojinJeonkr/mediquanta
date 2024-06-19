package com.application.mediquanta.hospital.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.hospital.dto.HospitalDTO;

@Mapper
public interface HospitalDAO {

	public List<HospitalDTO> getHospitalList();			// 병원 목록 가져오기
	
}
