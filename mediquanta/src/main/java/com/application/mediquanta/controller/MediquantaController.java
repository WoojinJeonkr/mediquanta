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
	
	@GetMapping("/term")
	public String term() {
		return "term";
	}
	
	@GetMapping("/privacy")
	public String privacy() {
		return "privacy";
	}
	
}
