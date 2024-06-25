package com.application.mediquanta.hospital.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.application.mediquanta.hospital.config.BasedList;
import com.application.mediquanta.hospital.config.HospitalApiConst;
import com.application.mediquanta.hospital.dto.HospitalApiDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HospitalApiManager {

	/**
     * 오픈 API 서버에서 데이터를 가져와서 알맞은 데이터 포맷으로 파싱하는 역할만 수행
     */

    /**
     * 핵심 조회 메소드
     * @throws ParseException 
     */
	
    // 전체 병원목록 조회
    public List<HospitalApiDTO> fetchByCode(int page, int numOfRows) throws ParseException {
        String hospitalUrl = makeListUrl(page, numOfRows);
        return fetch(hospitalUrl);
    }
    
    /**
     * 편의 private 메소드들
     */
    
    // 기본 URL 만들기
    private String makeBaseUrl(String apiUriType) {
    	String makeBaseUrl = HospitalApiConst.ENDPOINT + apiUriType + HospitalApiConst.SERVICE_KEY;
    	System.out.println("1. " + makeBaseUrl);
        return makeBaseUrl;
    }
    
    private String makeListUrl(int page, int numOfRows) {
    	String makeListUrl = makeBaseUrl(BasedList.SERVICE_KEY) + setPage(page) + setNumOfRows(numOfRows);
    	System.out.println("2. " + makeListUrl);
        return makeListUrl;
    }
    
    // 가져올 데이터 수 정하기
    private String setNumOfRows(int n) {
        if (n < 0 || n > 10000) {
            // 범위를 넘어가면 디폴트값 내리기
            return "&numOfRows=10";
        }
        return "&numOfRows=" + n;
    }
    
    // 가져올 페이지 수정하기
    private String setPage(int n) {
    	if (n < 0 || n > 8000) {
    		return "&pageNo=7778";
    	}
    	return "&numOfRows=" + n;
    }
    
 // 오픈 API 서버로부터 데이터 받아오기
    public List<HospitalApiDTO> fetch(String url) throws ParseException {
        List<HospitalApiDTO> result = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            
            // 가장 큰 JSON 객체 response 가져오기
            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
            log.info("response 파싱 완료 = {}", jsonResponse);

            // 그 다음 body 부분 파싱
            JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
            log.info("response->body 파싱 완료 = {}", jsonBody);

            // 그 다음 위치 정보를 배열로 담은 items 파싱
            JSONObject jsonItems = (JSONObject) jsonBody.get("items");
            log.info("response->body->items 파싱 완료 = {}", jsonItems);

            // items 는 JSON 임, 이제 그걸 또 배열로 가져온다
            JSONArray jsonItemList = (JSONArray) jsonItems.get("item");
            log.info("response->body->items->item 파싱 완료 = {}", jsonItemList);

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                HospitalApiDTO dto = makeHospitalDto(item);
                if (dto == null) continue;
                result.add(dto);
                log.info("{}", makeHospitalDto(item));
            }
            log.info("fetch 완료");
        } catch (Exception e) {
        	System.out.println("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        }
        return result;
    }
    
    // 콘텐츠 정보 JSON 을 DTO 로 변환
    private HospitalApiDTO makeHospitalDto(JSONObject item) {
        return HospitalApiDTO.builder()
        		.hospitalName((String) item.get("yadmNm"))
        		.type((String) item.get("clCdNm"))
        		.sidoCdNm((String) item.get("sidoCdNm"))
        		.sgguCdNm((String) item.get("sgguCdNm"))
        		.address((String) item.get("addr"))
        		.phone((String) item.get("telno"))
                .build();
    }

	
}
