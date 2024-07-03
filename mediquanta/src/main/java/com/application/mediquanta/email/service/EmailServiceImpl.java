package com.application.mediquanta.email.service;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.application.mediquanta.email.entity.EmailMessage;
import com.application.mediquanta.member.service.MemberService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private final MemberService memberService;

	@Override
	public String sendMail(EmailMessage emailMessage, String type) {
		String authNum = createCode();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		memberService.setTempPassword(emailMessage.getTo(), authNum);
		
        try {
        	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
			mimeMessageHelper.setText(setContext(authNum, type), true);
			javaMailSender.send(mimeMessage);
            log.info("Success");
            return authNum;
		} catch (MessagingException e) {
			log.info("fail");
            throw new RuntimeException(e);
		}
	}

	private String createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();
		
		for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
	}

	private String setContext(String code, String type) {
		Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
	}

}
