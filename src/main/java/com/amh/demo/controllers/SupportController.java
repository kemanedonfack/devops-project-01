package com.amh.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {
	
	@GetMapping("/support")
	public String getSupportPage() {
		return "support/support";
	}

}
