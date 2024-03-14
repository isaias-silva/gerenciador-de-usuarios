package com.zack.api.services;


import com.zack.api.components.files.MailHtmlGenerator;
import com.zack.api.dtos.EmailSendDto;
import com.zack.api.dtos.EmailSendToUserDto;
import com.zack.api.dtos.UserChangeRoleDto;
import com.zack.api.infra.queue.producers.MailProducer;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.roles.UserRole;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.UserData;
import com.zack.api.util.responses.bodies.UserDataForAdm;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdmService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailProducer admProducer;

    @Autowired
    MailHtmlGenerator mailHtmlGenerator;

    public UserData getUser(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException(GlobalResponses.USER_NOT_FOUND.getText());
        }
        return new UserData(user.get());
    }

    public List<UserData> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> users = userRepository.findAll(pageable);
        List<UserData> formatUsers = new ArrayList<>();

        users.forEach(userDb -> {

            formatUsers.add(new UserDataForAdm(userDb));

        });

        return formatUsers;
    }

    public Response changeRoleUser(UserChangeRoleDto userChangeRoleDto) throws IOException {
       if(userChangeRoleDto.role()== UserRole.VERIFY_MAIL){
           throw new NotFoundException(GlobalResponses.INVALID_ROLE.getText());
       }
        System.out.println(userChangeRoleDto.role());
        Optional<UserModel> user = userRepository.findById(userChangeRoleDto.id());
        if (user.isEmpty()) {
            throw new NotFoundException(GlobalResponses.USER_NOT_FOUND.getText());
        }

        UserModel userEntity = user.get();
        userEntity.setRole(userChangeRoleDto.role());
        userRepository.save(userEntity);

        sendMailAdmForQueue(userEntity.getMail(), userEntity.getName(), userChangeRoleDto.subject(), userChangeRoleDto.message());

        return new Response(GlobalResponses.USER_UPDATED.name());
    }

    public Response sendEmailToUser(EmailSendToUserDto emailSendToUserDto) throws IOException {
        UserData userData = getUser(emailSendToUserDto.userId());
        sendMailAdmForQueue(userData.getMail(), userData.getName(), emailSendToUserDto.subject(), emailSendToUserDto.content());

        return new Response(GlobalResponses.EMAIL_SEND.getText());
    }


    private void sendMailAdmForQueue(String userMail, String userName, String subject, String content) throws IOException {

        String htmlContent = mailHtmlGenerator.generatorMailFile(userName, content, Optional.empty());

        EmailSendDto emailSendDto = new EmailSendDto(userMail, subject, htmlContent);

        admProducer.publishMessageMail(emailSendDto);

    }
}
