package com.application.mips.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.mips.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	
	public void createMember(MemberDTO memberDTO); 	// 회원가입
	public String checkValidId(String memberId); 	// 아이디 중복 검사
	public MemberDTO login(String memberId);

}
