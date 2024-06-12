package com.application.mediquanta.member.service;

import com.application.mediquanta.dto.MemberDTO;

public interface MemberService {

	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public String checkValidNickname(String nickname);
	public String checkValidEmail(String email);
	public boolean login(MemberDTO memberDTO);
	
}
