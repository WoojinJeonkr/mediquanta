package com.application.mediquanta.service;

import com.application.mediquanta.dto.MemberDTO;

public interface MemberService {

	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public String checkNickname(String nickname);
	public boolean login(MemberDTO memberDTO);
	
}
