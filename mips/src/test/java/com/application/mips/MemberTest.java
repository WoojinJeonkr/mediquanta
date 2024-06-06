package com.application.mips;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.application.mips.dto.MemberDTO;
import com.application.mips.service.MemberService;

@SpringBootTest
public class MemberTest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberService memberService;
	
	@DisplayName("회원가입")
	@Test
	public void createMember() throws ParseException {
		
		MemberDTO memberDTO = new MemberDTO();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = formatter.parse("1997-10-05");
        Date createdAt = formatter.parse("2024-06-06 23:49:12");
        Date lastLogin = formatter.parse("2024-06-06 23:49:12");
		
		memberDTO.setMemberId("test1");
		memberDTO.setPasswd("test");
		memberDTO.setNickname("test");
		memberDTO.setEmail("test1@gmail.com");
		memberDTO.setAddress("");
		memberDTO.setBirth(birth);
		memberDTO.setGender("F");
		memberDTO.setRole("USER");
		memberDTO.setCreatedAt(createdAt);
		memberDTO.setLastLogin(lastLogin);
		
		memberService.createMember(memberDTO);
	}
	
}
