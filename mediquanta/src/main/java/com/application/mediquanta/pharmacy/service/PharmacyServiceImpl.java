package com.application.mediquanta.pharmacy.service;

import java.util.ArrayList;
import java.util.Collections;
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
import com.application.mediquanta.pharmacy.dao.PharmacyDAO;
import com.application.mediquanta.pharmacy.dto.PharmacyApiDTO;
import com.application.mediquanta.pharmacy.dto.PharmacyDTO;
import com.application.mediquanta.util.SgguCd;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {

	@Autowired
	private PharmacyApiManager pharmacyApiManager;
	
	@Autowired
	private PharmacyDAO pharmacyDAO;
	
	@Value("${kakao.rest-api.key}")
	private String kakaoApiKey;
	
	private final int DEFAULT_NUM_OF_ROWS = 100;

    private int dataCnt = 0;
    
    @Override
    public void savePharmacyListToDatabase() {
        try {
            for (SgguCd sgguCd : SgguCd.values()) {
                int currentPage = 1;
                boolean hasNextPage = true;

                while (hasNextPage) {
                    List<PharmacyApiDTO> pharmacyList = pharmacyApiManager.fetchPharmacyByCode(currentPage, DEFAULT_NUM_OF_ROWS, sgguCd);

                    if (pharmacyList.isEmpty()) {
                        hasNextPage = false;
                        continue;
                    }

                    for (PharmacyApiDTO pharmacy : pharmacyList) {
                        PharmacyDTO pharmacyDTO = new PharmacyDTO();
                        pharmacyDTO.setPharmacyName(pharmacy.getPharmacyName());
                        pharmacyDTO.setType(pharmacy.getType());
                        pharmacyDTO.setSidoCdNm(pharmacy.getSidoCdNm());
                        pharmacyDTO.setSgguCdNm(pharmacy.getSgguCdNm());
                        pharmacyDTO.setAddress(pharmacy.getAddress());
                        pharmacyDTO.setPhone(pharmacy.getPhone());
                        pharmacyDTO.setLatitude(pharmacy.getLatitude());
                        pharmacyDTO.setLongitude(pharmacy.getLongitude());
                        pharmacyDTO.setCreatedAt(new Date());
                        pharmacyDTO.setUpdatedAt(null);

                        pharmacyDAO.savePharmacy(pharmacyDTO);

                        dataCnt++;
                    }

                    log.info(sgguCd.toString() + " 지역코드 " + currentPage + "페이지의 약국 데이터 저장");
                    log.info("현재 저장된 데이터 개수: " + dataCnt + "개");

                    currentPage++;
                }
            }
        } catch (ParseException e) {
            log.warn("약국 데이터 가져오기 실패: " + e.getMessage());
        }
    }

	@Override
	public List<PharmacyDTO> getPharmacyList() {
		List<PharmacyDTO> pharmacyList = pharmacyDAO.getPharmacyList();
		if (pharmacyList.size() > 0) {
			return pharmacyList;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<PharmacyDTO> searchPharmacyByName(String name) {
		List<PharmacyDTO> pharmacyList = new ArrayList<PharmacyDTO>();
		pharmacyList.add(pharmacyDAO.searchPharmacyByName(name));
		return pharmacyList;
	}

	@Override
	public List<PharmacyDTO> searchPharmacyBySidoCdNm(String sidoCdNm) {
		return pharmacyDAO.searchPharmacyBySidoCdNm(sidoCdNm);
	}

	@Override
	public PharmacyDTO getPharmacyDetails(long pharmacyId) {
		return pharmacyDAO.getPharmacyDetails(pharmacyId);
	}
	
	@Override
	public List<Map<String, Object>> getPharmacyTypeCounts() {
		return pharmacyDAO.getPharmacyTypeCounts();
	}

	@Override
	public List<PharmacyDTO> selectNearestPharmacies(double latitude, double longitude) {
		Map<String, Object> params = new HashMap<>();
		params.put("latitude", latitude);
        params.put("longitude", longitude);
		return pharmacyDAO.selectNearestPharmacies(params);
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
	public void updatePharmacyInfo(PharmacyDTO pharmacyDTO) {
		pharmacyDTO.setUpdatedAt(new Date());
		pharmacyDAO.updatePharmacyInfo(pharmacyDTO);
	}

	@Override
	public void deletePharmacy(long pharmacyId) {
		pharmacyDAO.deletePharmacy(pharmacyId);
	}
    
}
