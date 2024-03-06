package com.zack.api.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    @Value(value="${spring.mail.username}")
    private String emailFrom;

    public void sendMail(StringBuilder body,
                         String subject,
                         String to){
       try {
           MimeMessage mimeMessage = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

           helper.setFrom(emailFrom);
           helper.setTo(to);
           helper.setSubject(subject);
           helper.setText(body.toString(),true);

           mailSender.send(mimeMessage);

       } catch (MessagingException e) {
           throw new RuntimeException(e);
       }
    }
}
