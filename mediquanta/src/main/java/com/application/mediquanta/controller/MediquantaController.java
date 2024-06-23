package com.application.mediquanta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MediquantaController {

	@GetMapping  
	public String main() {
		return "main";
	}
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	// TODO : 4. 이용약관 페이지 생성 및 페이지 이동 Controller 작성
	
	// TODO : 5. 개인정보보호 페이지 생성 및 페이지 이동 Controller 작성
	
}
