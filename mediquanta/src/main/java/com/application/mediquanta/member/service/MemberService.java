package com.application.mediquanta.member.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.application.mediquanta.member.dto.MemberDTO;

public interface MemberService {

	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;
	public String checkValidId(String memberId);
	public String checkValidNickname(String nickname);
	public String checkValidEmail(String email);
	public String checkRole(String memberId);
	public boolean login(MemberDTO memberDTO);
	public Map<String, Double> kakaoLocalAPI(String query);
	public MemberDTO getUserInfo(String memberId);
	public void updateMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;
	public void signOut(String memberId);
	public List<MemberDTO> getMemberList();
	public List<Map<String, String>> getProfileUUIDList();
	public Map<String, Integer> getRoleCount();
	public Map<String, Integer> getGenderCount();
	public Map<String, Integer> getActiveCount();
	public void setTempPassword(String email, String authNum);
	public void deleteMember();
}
