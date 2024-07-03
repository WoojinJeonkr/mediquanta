package com.application.mediquanta.pharmacy.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.application.mediquanta.pharmacy.config.PharmacyBasedList;
import com.application.mediquanta.pharmacy.config.PharmacyApiConst;
import com.application.mediquanta.pharmacy.dto.PharmacyApiDTO;
import com.application.mediquanta.util.SgguCd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PharmacyApiManager {

	public List<PharmacyApiDTO> fetchPharmacyByCode(int page, int numOfRows, SgguCd sgguCd) throws ParseException {
        String pharmacyUrl = makeListUrl(page, numOfRows, sgguCd);
        return fetch(pharmacyUrl);
    }
    
    private String makeBaseUrl(String apiUriType) {
    	String makeBaseUrl = PharmacyApiConst.ENDPOINT + apiUriType + PharmacyApiConst.SERVICE_KEY;
        return makeBaseUrl;
    }
    
    private String makeListUrl(int page, int numOfRows, SgguCd sgguCd) {
    	String makeListUrl = makeBaseUrl(PharmacyBasedList.SERVICE_KEY) + setPage(page) + setNumOfRows(numOfRows) + setSgguCd(sgguCd);
        return makeListUrl;
    }
    
    private String setNumOfRows(int n) {
        return "&numOfRows=" + n;
    }
    
    private String setPage(int n) {
    	return "&pageNo=" + n;
    }
    
    private String setSgguCd(SgguCd sgguCd) {
    	return "&sgguCd=" + sgguCd.toString();
    }
    
    public List<PharmacyApiDTO> fetch(String url) throws ParseException {
        List<PharmacyApiDTO> result = new ArrayList<>();
        try {
	        RestTemplate restTemplate = new RestTemplate();
	        String jsonString = restTemplate.getForObject(url, String.class);
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
	        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
	        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
	        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
	        JSONArray jsonItemList = (JSONArray) jsonItems.get("item");
	
	        for (Object o : jsonItemList) {
	            JSONObject item = (JSONObject) o;
	            PharmacyApiDTO dto = makePharmacyDto(item);
	            if (dto == null) continue;
	            result.add(dto);
	        }
	        
	        log.info("fetch 완료");
	        
        } catch (Exception e) {
            log.warn("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        }
        
        return result;
    }
    
    private PharmacyApiDTO makePharmacyDto(JSONObject item) {
    	if (item.get("YPos") instanceof String || item.get("XPos") instanceof String
                || item.get("yadmNm") == null || item.get("clCdNm") == null
                || item.get("sidoCdNm") == null || item.get("sgguCdNm") == null
                || item.get("addr") == null || item.get("telno") == null) {
            return null;
        }
    	
    	return PharmacyApiDTO.builder()
        		.pharmacyName((String) item.get("yadmNm"))
        		.type((String) item.get("clCdNm"))
        		.sidoCdNm((String) item.get("sidoCdNm"))
        		.sgguCdNm((String) item.get("sgguCdNm"))
        		.address((String) item.get("addr"))
        		.phone((String) item.get("telno"))
        		.latitude((double) item.get("YPos"))
        		.longitude((double) item.get("XPos"))
                .build();
    }
    
}
