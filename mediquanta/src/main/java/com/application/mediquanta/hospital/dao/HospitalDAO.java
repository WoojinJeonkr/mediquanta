package com.application.mediquanta.hospital.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.hospital.dto.HospitalDTO;

@Mapper
public interface HospitalDAO {
	public void saveHospital(HospitalDTO hospitalDTO);
}
