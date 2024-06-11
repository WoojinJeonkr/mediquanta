package com.application.mediquanta.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberDTO {
	
	private Long memberIdx;		// 회원 index
	private String memberId;	// 회원_ID
	private String passwd; 		// 비밀번호
	private String nickname;	// 닉네임
	private String email;		// 이메일
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;			// 생년월일
	private String gender;		// 성별
	private String zipcode;		// 우편번호
	private String roadAddress;	// 도로명주소
	private String landAddress;	// 지번 주소
	private String etcAddress;	// 기타 주소
	private String role;		// 권한
	private String activeYn;    // 회원 활성화 여부
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;		// 회원 등록 날짜
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastLogin;		// 최종 로그인

}
