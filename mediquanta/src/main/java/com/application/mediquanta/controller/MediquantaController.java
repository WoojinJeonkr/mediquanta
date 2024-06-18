package com.application.mediquanta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MediquantaController {

	@GetMapping
	public String main() {
		return "/main";
	}
	
}
