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
		// TODO : 2. 사이트에 맞게 HTML 내용 변경하기
		return "about";
	}
	
	// TODO : 3. Error 404 (페이지를 찾을 수 없음)인 경우 Error 404 페이지를 보여주게끔 처리하기
	// https://library.livecanvas.com/starters/category/404/
	
	// TODO : 4. 이용약관 페이지 생성 및 페이지 이동 Controller 작성
	
	// TODO : 5. 개인정보보호 페이지 생성 및 페이지 이동 Controller 작성
	
}
