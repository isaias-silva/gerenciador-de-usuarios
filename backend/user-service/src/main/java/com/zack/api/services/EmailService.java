package com.zack.api.services;


import com.zack.api.models.EmailModel;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.EmailRepository;
import com.zack.api.repositories.UserRepository;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.bodies.MailData;
import com.zack.api.util.responses.bodies.MailDataDetails;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.enums.GlobalResponses;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<MailData> getMails(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object> emailsDb = emailRepository.getResumeEmails(pageable);
        List<MailData> formatMails = new ArrayList<>();

        emailsDb.getContent().forEach(model -> {
            formatMails.add(new MailData(model));
        });

        return formatMails;
    }

    public MailDataDetails getMailDetails(UUID id) {
        Optional<EmailModel> emailModel = emailRepository.findById(id);
        if (emailModel.isEmpty()) {
            throw new NotFoundException(GlobalResponses.EMAIL_NOT_FOUND.getText());
        } else {
            var email = emailModel.get();
            return new MailDataDetails(email.getId(),
                    email.getSubject(),
                    email.getToMail(),
                    email.getFromMail(),
                    email.getContent());
        }

    }
    public Response deleteMail(UUID id){
        Optional<EmailModel> emailExist = emailRepository.findById(id);
        if (emailExist.isEmpty()) {
            throw new NotFoundException(GlobalResponses.EMAIL_NOT_FOUND.getText());
        }else{
            emailRepository.delete(emailExist.get());
            return new Response(GlobalResponses.EMAIL_DELETED.getText());
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
