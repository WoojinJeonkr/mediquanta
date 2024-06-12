package com.application.mediquanta.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.application.mediquanta.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	
	public void createMember(MemberDTO memberDTO); 		// 회원가입
	public String checkValidId(String memberId); 		// 아이디 중복 검사
	public String checkValidNickname(String nickname); 	// 닉네임 중복 검사
	public String checkValidEmail(String email);		// 이메일 중복 검사
	public MemberDTO login(String memberId);			// 로그인
	public MemberDTO getMemberInfo(String memberId);	// 회원 정보 가져오기
	public void updateMember(MemberDTO memberDTO);		// 회원 정보 수정
	public void signOut(String memberId);				// 회원 탈퇴
	public void deleteMember(String memberId);			// 회원 삭제

}
