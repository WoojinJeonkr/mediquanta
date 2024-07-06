package com.application.mediquanta.member.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.application.mediquanta.address.dto.Documents;
import com.application.mediquanta.address.dto.LocationInfoRes;
import com.application.mediquanta.member.dao.MemberDAO;
import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.dto.UpdateLastLoginRequest;

@Service
public class MemberServiceImpl implements MemberService {

	@Value("${file.repo.path}")
    private String fileRepositoryPath;
	
	@Value("${kakao.rest-api.key}")
	private String kakaoApiKey;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException {
		
		if (!uploadProfile.isEmpty()) { 													 
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setProfileUUID(uploadFile);
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
		}
		
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDTO.setGender(memberDTO.getGender());
		memberDTO.setBirth(memberDTO.getBirth());
		memberDTO.setActiveYn("y");
		memberDTO.setEtcAddress(memberDTO.getEtcAddress() == null ? "" : memberDTO.getEtcAddress());
		memberDTO.setRole(memberDTO.getMemberId().equals("admin") ? "ADMIN" : "USER");
		memberDTO.setCreatedAt(new Date());
		memberDTO.setLastLogin(new Date());
		
		memberDAO.createMember(memberDTO);
	}

	@Override
	public String checkValidId(String memberId) {
		String isValidId = "n";
		if (memberDAO.checkValidId(memberId) == null) {
		      isValidId = "y";
	    }
		return isValidId;
	}
	
	@Override
	public String checkValidNickname(String nickname) {
		String isValidNickname = "n";
		String checkNickname = memberDAO.checkValidNickname(nickname);
		if (checkNickname == null || checkNickname.equals(nickname)) {
			isValidNickname = "y";
		}
		return isValidNickname;
	}
	
	@Override
	public String checkValidEmail(String email) {
		String isValidEmail = "n";
		String checkEmail = memberDAO.checkValidEmail(email);
		if (checkEmail == null || checkEmail.equals(email)) {
			isValidEmail = "y";
		}
		return isValidEmail;
	}
	
	@Override
	public String checkRole(String memberId) {
		return memberDAO.checkRole(memberId);
	}
	
	@Override
	public boolean login(MemberDTO memberDTO) {
		
		MemberDTO validateData = memberDAO.login(memberDTO.getMemberId());
		if (validateData != null) {
			if (passwordEncoder.matches(memberDTO.getPasswd(), validateData.getPasswd())
					&& validateData.getActiveYn().equals("y")) {
				UpdateLastLoginRequest loginRequest = new UpdateLastLoginRequest();
				loginRequest.setMemberId(memberDTO.getMemberId());
				loginRequest.setLastLogin(new Date());
				memberDAO.updateLastLogin(loginRequest);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public MemberDTO getUserInfo(String memberId) {
		return memberDAO.getUserInfo(memberId);
	}
	
	@Override
	public List<Map<String, String>> getProfileUUIDList() {
		return memberDAO.getProfileUUIDList();
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
	public void updateMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException {
		
		if (!uploadProfile.isEmpty()) {	
			new File(fileRepositoryPath + memberDTO.getProfileUUID()).delete();
			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);
			String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			String uploadFile = UUID.randomUUID() + extension;
			memberDTO.setProfileUUID(uploadFile);
			uploadProfile.transferTo(new File(fileRepositoryPath + uploadFile));
		}
		
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDTO.setEtcAddress(memberDTO.getEtcAddress() == null ? "" : memberDTO.getEtcAddress());
		memberDTO.setActiveYn("y");
		memberDAO.updateMember(memberDTO);
	}

	@Override
	public void signOut(String memberId) {
		MemberDTO memberDTO = memberDAO.getUserInfo(memberId);
		memberDTO.setActiveYn("n");
		memberDAO.updateMember(memberDTO);
	}

	@Override
	public List<MemberDTO> getMemberList() {
		List<MemberDTO> memberList = memberDAO.getMemberList();
		if (memberList.size() > 0) {
			return memberList;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Map<String, Integer> getRoleCount() {
		return memberDAO.getRoleCount();
	}

	@Override
	public Map<String, Integer> getGenderCount() {
		return memberDAO.getGenderCount();
	}

	@Override
	public Map<String, Integer> getActiveCount() {
		return memberDAO.getActiveCount();
	}

	@Override
	public void setTempPassword(String email, String passwd) {
		memberDAO.setTempPassword(email, passwordEncoder.encode(passwd));
	}

	@Override
	public void deleteMember() {
		memberDAO.deleteMember(new Date());
	}

}
