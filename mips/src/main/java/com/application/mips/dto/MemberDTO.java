package com.application.mips.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {

	/*
		회원_ID varchar(20) [primary key]
		비밀번호 varchar(30)
		닉네임 varchar(50)
		이메일 varchar(50)
		생년월일 timestamp
		성별 varchar(6)
		주소 varchar(50)
		권한 varchar(5)
		회원_등록_날짜 timestamp
		최종_로그인 timestamp
	*/
	
	private String memberId;
	private String passwd;
	private String nickname;
	private String email;
	private Date birth;
	private String gender;
	private String address;
	private String role;
	private Date createdAt;
	private Date lastLogin;

}
