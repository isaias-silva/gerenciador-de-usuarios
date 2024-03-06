package com.zack.api.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    @Value(value="${spring.mail.username}")
    private String emailFrom;

    public void sendMail(String text,
                         String subject,
                         String to){
       try {
           SimpleMailMessage message = new SimpleMailMessage();
           message.setSubject(subject);
           message.setTo(to);
           message.setFrom(emailFrom);
           message.setText(text);

           mailSender.send(message);

       }catch(MailException err){

           throw new RuntimeException(err);
       }
    }
}
