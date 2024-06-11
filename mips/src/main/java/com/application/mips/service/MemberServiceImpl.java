package com.application.mips.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.mips.dao.MemberDAO;
import com.application.mips.dto.MemberDTO;

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
		memberDTO.setRole("USER");
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
	public String checkNickname(String nickname) {
		String isValidNickname = "n";
		if (memberDAO.checkValidNickname(nickname) == null) {
			isValidNickname = "y";
		}
		return isValidNickname;
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

}
