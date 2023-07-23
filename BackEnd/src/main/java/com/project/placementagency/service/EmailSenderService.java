package com.project.placementagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * EmailSenderService
 */
@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to,String subject,String body) throws MessagingException
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        mailSender.send(mimeMessage);
        // SimpleMailMessage msg = new SimpleMailMessage();
        // msg.setFrom("avchandan1@gmail.com");
        // msg.setTo(to);
        // msg.setText(body);
        // msg.setSubject(subject);

        // mailSender.send(msg);
    }
    
}