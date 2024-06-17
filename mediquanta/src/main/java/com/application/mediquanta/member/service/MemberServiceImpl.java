package com.application.mediquanta.member.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.mediquanta.member.dao.MemberDAO;
import com.application.mediquanta.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createMember(MemberDTO memberDTO) {
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDTO.setGender(memberDTO.getGender().equals("female") ? "F" : "M");
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
	public void updateMember(MemberDTO memberDTO) {
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDTO.setEtcAddress(memberDTO.getEtcAddress() == null ? "" : memberDTO.getEtcAddress());
		memberDAO.updateMember(memberDTO);
	}

}
