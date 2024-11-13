package com.lgs.api.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class SendMail {
	
	@Autowired
	private JavaMailSender mailSender;

//	public void sendEmail(String to, String subject, String text) {
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setTo(to);
//		mailMessage.setSubject(subject);
//		mailMessage.setText(text);
//		mailSender.send(mailMessage);
//	}
	
	 public void sendEmail(String to, String subject, String htmlBody) throws MessagingException {
	        MimeMessage mimeMessage = mailSender.createMimeMessage();

	        // Use the true flag to indicate that you want to send HTML content
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(htmlBody, true); // Set the HTML body

	        mailSender.send(mimeMessage);
	    }
}
