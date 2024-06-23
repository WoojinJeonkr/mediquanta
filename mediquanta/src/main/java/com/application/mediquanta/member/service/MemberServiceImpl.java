package com.application.mediquanta.member.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.mediquanta.member.dao.MemberDAO;
import com.application.mediquanta.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Value("${file.repo.path}")
    private String fileRepositoryPath;
	
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
		if (memberDAO.checkValidNickname(nickname) == null) {
			isValidNickname = "y";
		}
		return isValidNickname;
	}
	
	@Override
	public String checkValidEmail(String email) {
		String isValidEmail = "n";
		if (memberDAO.checkValidEmail(email) == null) {
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
			return null;
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

}
