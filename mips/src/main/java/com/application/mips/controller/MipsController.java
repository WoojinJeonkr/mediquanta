package com.application.mips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mips")
public class MipsController {

	@GetMapping
	public String main() {
		return "/member/main";
	}
	
}
