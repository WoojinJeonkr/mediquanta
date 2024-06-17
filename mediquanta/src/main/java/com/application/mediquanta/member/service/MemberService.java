package com.application.mediquanta.member.service;

import com.application.mediquanta.member.dto.MemberDTO;

public interface MemberService {

	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public String checkValidNickname(String nickname);
	public String checkValidEmail(String email);
	public String checkRole(String memberId);
	public MemberDTO getUserInfo(String memberId);
	public void updateMember(MemberDTO memberDTO);
	public boolean login(MemberDTO memberDTO);
	
}
