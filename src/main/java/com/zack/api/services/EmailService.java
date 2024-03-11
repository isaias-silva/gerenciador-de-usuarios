package com.zack.api.services;


import com.zack.api.models.EmailModel;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.EmailRepository;
import com.zack.api.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    UserRepository userRepository;
    final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public void sendMail(StringBuilder content,
                         String subject,
                         String to) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content.toString(), true);

            saveMail(to, subject, content);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveMail(String to, String subject, StringBuilder content) {
        UserModel user = userRepository.findOneByEmail(to);
        EmailModel emailModel = new EmailModel();

        emailModel.setFromMail(emailFrom);
        emailModel.setToMail(to);
        emailModel.setSubject(subject);
        emailModel.setContent(content.toString());
        emailModel.setUserMail(user);

        emailRepository.save(emailModel);

    }
}
