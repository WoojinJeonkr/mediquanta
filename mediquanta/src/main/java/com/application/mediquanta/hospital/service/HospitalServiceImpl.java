package com.application.mediquanta.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.hospital.dao.HospitalDAO;
import com.application.mediquanta.hospital.dto.HospitalDTO;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalDAO hospitalDAO;
	
	@Override
	public List<HospitalDTO> getHospitalList() {
		return hospitalDAO.getHospitalList();
	}

}
