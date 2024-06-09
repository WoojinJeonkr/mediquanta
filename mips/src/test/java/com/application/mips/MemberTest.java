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
		
		boolean isValidMember = false;
		if (memberService.checkValidId(memberDTO.getMemberId()) == "y") {
			isValidMember = true;
		} else if (memberService.checkNickname(memberDTO.getNickname()) == "y") {
			isValidMember = true;
		} else {
			isValidMember = false;
			System.out.println("중복된 아이디입니다.");
		}
		
		if (isValidMember) memberService.createMember(memberDTO);
		
	}
	
	@Test
	public void login() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId("test1");
		
		/*
		 	[BCryptPasswordEncoder encode 비교]
		 	
		 	BCryptPasswordEncoder를 사용하여 패스워드를 암호화할 때마다 생성되는 해시 값은 무작위하고
		 	salt라 불리는 추가적인 비밀 값을 사용합니다.
		 	이 salt는 매번 다르게 생성되기 때문에 같은 입력 값에 대해 항상 같은 해시가 생성되지 않습니다.
		 	
		 	만약 memberDTO.setPasswd(passwordEncoder.encode("test"));로 설정하고
		 	System.out.println(passwordEncoder.encode("test").equals(memberDTO.getPasswd()));
		 	결과를 확인한다면
		 	passwordEncoder.encode("test")를 호출할 때마다 새로운 salt와 함께 생성된 해시 값이 반환되므로,
		 	equals() 메서드를 통해 비교하면 항상 false가 반환됩니다.
		 	
		 	또한 만약 기존에 저장된 암호화된 패스워드인 encodedPassword와 비교하려는 새로운 비밀번호 "test"를
		 	passwordEncoder.encode("test")로 암호화한 값과 비교하고자 한다면, 비교가 항상 false가 됩니다.
		 	
		 	따라서 BCryptPasswordEncoder를 통한 password를 비교할 때는 matches() 메서드를 사용해야 합니다.
		 */
		
		memberDTO.setPasswd("test");
		String resultMsg;
		if (memberDTO.getMemberId().equals("test1") && passwordEncoder.matches(memberDTO.getPasswd(), passwordEncoder.encode("test"))) {
			resultMsg = "로그인 성공";
		} else {
			resultMsg = "로그인 실패";
		}
		System.out.println(resultMsg);
	}
}
