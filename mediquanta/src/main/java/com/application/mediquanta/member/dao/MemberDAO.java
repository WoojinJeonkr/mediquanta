package com.application.mediquanta.member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.application.mediquanta.member.dto.MemberDTO;
import com.application.mediquanta.member.dto.UpdateLastLoginRequest;

@Mapper
public interface MemberDAO {
	
	public void createMember(MemberDTO memberDTO); 													// 회원가입
	public String checkValidId(String memberId); 													// 아이디 중복 검사
	public String checkValidNickname(String nickname); 												// 닉네임 중복 검사
	public String checkValidEmail(String email);													// 이메일 중복 검사
	public String checkRole(String memberId);														// 회원 권한 조회
	public void updateLastLogin(UpdateLastLoginRequest request); 									// 마지막 로그인 시간 최신화
	public MemberDTO login(String memberId);														// 로그인
	public MemberDTO getUserInfo(String memberId);													// 단일 회원 정보 가져오기
	public void updateMember(MemberDTO memberDTO);													// 회원 정보 수정
	public void signOut(String memberId);															// 회원 탈퇴
	public void deleteMember(Date date);														// 회원 삭제
	public List<MemberDTO> getMemberList();															// 전체 회원 목록 조회
	public Map<String, Integer> getRoleCount();														// 회원 권한 현황 조회
	public Map<String, Integer> getGenderCount();													// 회원 성별 현황 조회
	public Map<String, Integer> getActiveCount();													// 회원 상태 현황 조회
	public void setTempPassword(@Param("email") String email, @Param("authNum") String authNum);	// 비밀번호 재발급
	
}
