package com.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.model.EmailRequest;
import com.email.service.EmailService;

@RestController
public class emailcontroller {
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/welcome")
	public String welcome() {
		
		return "This is the welcome page of the App";
	}
	
	//api to send email
	@PostMapping("/sendmail")
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){
		
//		emailService.sendEmail(, null, null)
	   System.out.println(request);
	 boolean result= emailService.sendEmail(request.getSubject(), request.getMessage(),request.getTo());
	 if(result) {
		 return ResponseEntity.ok("Email is sent successfully");
	 }else
	     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent");
	}

}
