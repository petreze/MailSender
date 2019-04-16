package com.frantishex.mailsender.controller;
	 
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.frantishex.mailsender.services.MailService;

@RestController
@Transactional
public class MailSenderController {

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/email/send", method = RequestMethod.POST, params = { "email", "subject", "message" })
	private ResponseEntity<String> sendEmailToUser(String email, String subject, String message) {
		try {
			mailService.saveEmail(email, subject, message);
		} catch (Throwable e) {
			
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>("Message sent!", HttpStatus.OK);
	}
}