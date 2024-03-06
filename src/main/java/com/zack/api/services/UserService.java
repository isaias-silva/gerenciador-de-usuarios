package com.zack.api.services;

import com.zack.api.components.crypt.Hash;
import com.zack.api.components.files.MailHtmlGenerator;
import com.zack.api.components.utils.GenerateRandom;
import com.zack.api.dtos.EmailSendDto;
import com.zack.api.dtos.UserCreateDto;
import com.zack.api.dtos.UserLoginDto;
import com.zack.api.dtos.UserUpdateDto;
import com.zack.api.infra.queue.producers.UserProducer;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.roles.UserRole;
import com.zack.api.util.exceptions.ForbiddenException;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseJwt;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.zack.api.util.responses.enums.GlobalResponses.USER_ALREADY_EXISTS;

@Service("userService")
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserProducer userProducer;
    @Autowired
    Hash hash;
    @Autowired
    GenerateRandom generateRandom;
    @Autowired
    MailHtmlGenerator mailHtmlGenerator;

    @Transactional

    public Response registerUser(UserCreateDto userDoc) throws IOException {

        UserModel exists = userRepository.findOneByUsernameOrEmail(userDoc.name(), userDoc.mail());
        if (exists != null) {
            throw new BadRequestException(USER_ALREADY_EXISTS.getText());
        }

        sendMailRegisterForQueue(userDoc.mail(), userDoc.name());
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDoc, userModel);
        userModel.setRole(UserRole.VERIFY_MAIL);
        userModel.setPassword(hash.generateHash(userModel.getPassword()));

        ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));

        return new Response(GlobalResponses.USER_REGISTERED.getText());
    }


    public Response loginUser(UserLoginDto userDoc) throws AccountNotFoundException, BadRequestException {
        var userdata = userRepository.findOneByEmail(userDoc.mail());
        if (userdata == null) {
            throw new NotFoundException(GlobalResponses.USER_NOT_FOUND.getText());
        }


        if (!(hash.compareHash(userDoc.password(), userdata.getPassword()))) {
            throw new BadRequestException((GlobalResponses.USER_INCORRECT_PASSWORD.getText()));
        } else {
            String token = tokenService.generateToken(userdata);

            return new ResponseJwt(GlobalResponses.USER_LOGGED.getText(), token);
        }

    }

    public Map<String, String> getMe() throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            UserModel user = (UserModel) authentication.getPrincipal();

            Map<String, String> formatUserData = new HashMap<>();
            formatUserData.put("name", user.getName());
            formatUserData.put("mail", user.getMail());
            formatUserData.put("profile", user.getProfile());
            formatUserData.put("resume", user.getResume());
            formatUserData.put("role", user.getRole().name());

            return formatUserData;
        } else {
            throw new NotFoundException((GlobalResponses.USER_NOT_FOUND.getText()));
        }

    }


    public Response updateUser(UserUpdateDto data) throws BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            UserModel user = (UserModel) authentication.getPrincipal();

            if (data.name() != null) user.setName(data.name());
            if (data.mail() != null) user.setMail(data.mail());
            if (data.resume() != null) user.setResume(data.resume().toString());

            userRepository.save(user);

            return new Response(GlobalResponses.USER_UPDATED.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }
    }

    public Response validateMail(String code) throws BadRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserModel user = (UserModel) authentication.getPrincipal();
            if(user.getRole()!=UserRole.VERIFY_MAIL){
                throw new BadRequestException(GlobalResponses.MAIL_VALIDATED.getText());
            }

            String cacheCode=generateRandom.getCodeFromCache(user.getMail());
            if(Objects.equals(cacheCode, code)){
                user.setRole(UserRole.USER);
                userRepository.save(user);
                generateRandom.clearCache(user.getMail());

                return new Response(GlobalResponses.MAIL_VALIDATED.getText());
            }else{
                throw new ForbiddenException(GlobalResponses.INVALID_CODE.getText());
            }

        } else {
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }
    }

    private void sendMailRegisterForQueue(String mail, String name) throws IOException {
        String random= generateRandom.randomCode(mail);

        EmailSendDto emailSendDto = new EmailSendDto(
                mail,
                "bem vindo!",
                mailHtmlGenerator.generatorMailFile(
                        "default.verify",
                        name,
                        "seja bem vindo a plataforma, para validar seu e-mail use o código:",
                        Optional.of(random)));



        userProducer.publishMessageMail(emailSendDto);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

}
