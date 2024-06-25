package com.application.mediquanta.hospital.service;

import java.util.Date;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mediquanta.hospital.dao.HospitalDAO;
import com.application.mediquanta.hospital.dto.HospitalApiDTO;
import com.application.mediquanta.hospital.dto.HospitalDTO;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
    private HospitalApiManager hospitalApiManager;
    
    @Autowired
    private HospitalDAO hospitalDAO;
    
    private final int DEFAULT_PAGE_NO = 7778;
	private final int DEFAULT_NUM_OF_ROWS = 10;

    @Override
    public void saveHospitalsToDatabase() {
        try {
            List<HospitalApiDTO> hospitals = hospitalApiManager.fetchByCode(DEFAULT_PAGE_NO, DEFAULT_NUM_OF_ROWS);
            for (HospitalApiDTO hospital : hospitals) {
            	HospitalDTO hospitalDTO = new HospitalDTO();
            	hospitalDTO.setHospitalName(hospital.getHospitalName());
            	hospitalDTO.setType(hospital.getType());
            	hospitalDTO.setSidoCdNm(hospital.getSidoCdNm());
            	hospitalDTO.setSgguCdNm(hospital.getSgguCdNm());
            	hospitalDTO.setAddress(hospital.getAddress());
            	hospitalDTO.setPhone(hospital.getPhone());
            	hospitalDTO.setCreatedAt(new Date());
            	hospitalDTO.setUpdatedAt(null);
            	
                hospitalDAO.saveHospital(hospitalDTO);
            }
            System.out.println("병원 데이터 저장 완료");
        } catch (ParseException e) {
            System.err.println("병원 데이터 가져오기 실패: " + e.getMessage());
        }
    }
	
}