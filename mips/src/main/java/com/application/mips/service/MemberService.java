package com.application.mips.service;

import com.application.mips.dto.MemberDTO;

public interface MemberService {

	public void createMember(MemberDTO memberDTO);
	public String checkValidId(String memberId);
	public boolean login(MemberDTO memberDTO);
	
}
