package com.application.mediquanta.email.service;

import com.application.mediquanta.email.entity.EmailMessage;

public interface EmailService {
	public String sendMail(EmailMessage emailMessage, String type);
}
