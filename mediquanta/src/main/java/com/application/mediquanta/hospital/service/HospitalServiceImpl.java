package com.application.mediquanta.hospital.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.application.mediquanta.address.dto.Documents;
import com.application.mediquanta.address.dto.LocationInfoRes;
import com.application.mediquanta.hospital.dao.HospitalDAO;
import com.application.mediquanta.hospital.dto.HospitalApiDTO;
import com.application.mediquanta.hospital.dto.HospitalDTO;
import com.application.mediquanta.util.SgguCd;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalApiManager hospitalApiManager;

    @Autowired
    private HospitalDAO hospitalDAO;
    
    @Value("${kakao.rest-api.key}")
	private String kakaoApiKey;

    private final int DEFAULT_NUM_OF_ROWS = 100;

    private int dataCnt = 0;

    @Override
    public void saveHospitalsToDatabase() {
        try {
            for (SgguCd sgguCd : SgguCd.values()) {
                int currentPage = 1;
                boolean hasNextPage = true;

                while (hasNextPage) {
                    List<HospitalApiDTO> hospitals = hospitalApiManager.fetchByCode(currentPage, DEFAULT_NUM_OF_ROWS, sgguCd);

                    if (hospitals.isEmpty()) {
                        hasNextPage = false;
                        continue;
                    }

                    for (HospitalApiDTO hospital : hospitals) {
                        HospitalDTO hospitalDTO = new HospitalDTO();
                        hospitalDTO.setHospitalName(hospital.getHospitalName());
                        hospitalDTO.setType(hospital.getType());
                        hospitalDTO.setSidoCdNm(hospital.getSidoCdNm());
                        hospitalDTO.setSgguCdNm(hospital.getSgguCdNm());
                        hospitalDTO.setAddress(hospital.getAddress());
                        hospitalDTO.setPhone(hospital.getPhone());
                        hospitalDTO.setLatitude(hospital.getLatitude());
                        hospitalDTO.setLongitude(hospital.getLongitude());
                        hospitalDTO.setHospitalUrl(hospital.getHospitalUrl());
                        hospitalDTO.setCreatedAt(new Date());
                        hospitalDTO.setUpdatedAt(null);

                        hospitalDAO.saveHospital(hospitalDTO);

                        dataCnt++;
                    }

                    log.info(sgguCd.toString() + " 지역코드 " + currentPage + "페이지의 병원 데이터 저장");
                    log.info("현재 저장된 데이터 개수: " + dataCnt + "개");

                    currentPage++;
                }
            }
        } catch (ParseException e) {
            log.warn("병원 데이터 가져오기 실패: " + e.getMessage());
        }
    }

	@Override
	public List<HospitalDTO> getHospitalList() {
		return hospitalDAO.getHospitalList();
	}

	@Override
	public List<HospitalDTO> searchHospitalByName(String name) {
		List<HospitalDTO> hospitalList = new ArrayList<HospitalDTO>();
		hospitalList.add(hospitalDAO.searchHospitalByName(name));
		return hospitalList;
	}

	@Override
	public List<HospitalDTO> searchHospitalBySidoCdNm(String sidoCdNm) {
		return hospitalDAO.searchHospitalBySidoCdNm(sidoCdNm);
	}

	@Override
	public List<HospitalDTO> searchHospitalByType(String type) {
		return hospitalDAO.searchHospitalByType(type);
	}

	@Override
	public HospitalDTO getHospitalDetails(long hospitalId) {
		return hospitalDAO.getHospitalDetails(hospitalId);
	}
	
	@Override
	public List<HospitalDTO> selectNearestHospitals(double latitude, double longitude) {
		Map<String, Object> params = new HashMap<>();
		params.put("latitude", latitude);
        params.put("longitude", longitude);
		return hospitalDAO.selectNearestHospitals(params);
	}
	
	@Override
	 public List<Map<String, Object>> getHospitalTypeCounts() {
		return hospitalDAO.getHospitalTypeCounts();
    }
	
	@Override
	public Map<String, Double> kakaoLocalAPI(String query) {
		String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + query;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+ kakaoApiKey);
        headers.set("content-type", "application/json;charset=UTF-8");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<LocationInfoRes> responseLocationInfo = restTemplate.exchange(url, HttpMethod.GET, entity, LocationInfoRes.class);
        Documents[] locationDoc = responseLocationInfo.getBody().getDocuments().clone();
        Map<String, Double> location = new HashMap<String, Double>();
        location.put("latitude", locationDoc[0].getY());
        location.put("longitude", locationDoc[0].getX());
        return location;
	}

	@Override
	public void updateHospInfo(HospitalDTO hospitalDTO) {
		hospitalDTO.setUpdatedAt(new Date());
		hospitalDAO.updateHospInfo(hospitalDTO);
	}

	@Override
	public void deleteHospital(long hospitalId) {
		hospitalDAO.deleteHospital(hospitalId);
	}
	
}