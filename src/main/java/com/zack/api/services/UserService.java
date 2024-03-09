package com.zack.api.services;

import com.zack.api.components.crypt.Hash;
import com.zack.api.components.files.MailHtmlGenerator;
import com.zack.api.components.utils.CacheUtils;
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
import com.zack.api.util.mail.MailTemplate;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseJwt;
import com.zack.api.util.responses.enums.GlobalResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    CacheUtils cacheUtils;
    @Autowired
    MailHtmlGenerator mailHtmlGenerator;


    @Transactional

    public Response registerUser(UserCreateDto userDoc) throws IOException {

        UserModel exists = userRepository.findOneByUsernameOrEmail(userDoc.name(), userDoc.mail());
        if (exists != null) {
            throw new BadRequestException(GlobalResponses.USER_ALREADY_EXISTS.getText());
        }

        sendMailForQueue(userDoc.mail(),
                userDoc.name(),
                MailTemplate.CODE_VERIFICATION,
                Optional.of(cacheUtils.cacheRandomCode(userDoc.mail())));
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

    public Response updateUser(UserUpdateDto data) throws IOException {

        UserModel user = getAuth();

        if (data.name() != null) user.setName(data.name());
        if (data.resume() != null) user.setResume(data.resume().toString());

        userRepository.save(user);

        if (data.mail() != null) {
            return changeMail(data.mail(), user);

        } else {
            return new Response(GlobalResponses.USER_UPDATED.getText());
        }
    }

    public Response validateMail(String code) throws IOException {

        UserModel user = getAuth();
        if (user.getRole() != UserRole.VERIFY_MAIL) {
            throw new BadRequestException(GlobalResponses.MAIL_ALREADY_VALIDATED.getText());
        }

        String cacheCode = cacheUtils.getCodeFromCache(user.getMail());
        if (Objects.equals(cacheCode, code)) {
            user.setRole(UserRole.USER);
            userRepository.save(user);
            cacheUtils.clearCacheRandom(user.getMail());

            sendMailForQueue(user.getMail(),
                    user.getName(),
                    MailTemplate.EMAIL_VALIDATED
                    , Optional.empty());
            return new Response(GlobalResponses.MAIL_VALIDATED.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.INVALID_CODE.getText());
        }

    }

    public Response sendNewCode() throws IOException {

        UserModel user = getAuth();
        cacheUtils.clearCacheRandom(user.getMail());

        String newCode = cacheUtils.getCodeFromCache(user.getMail());

        sendMailForQueue(user.getMail(),
                user.getName(),
                MailTemplate.NEW_CODE_VERIFICATION,
                Optional.of(newCode)
        );

        return new Response(GlobalResponses.NEW_CODE_SEND.getText());

    }

    public Response confirmNewMail(String code) {
        UserModel user = getAuth();
        String userId = user.getId().toString();

        String cacheCode = cacheUtils.getCodeFromCache(user.getMail());
        if (Objects.equals(cacheCode, code)) {
            String newMail = cacheUtils.getCachedNewMail(user.getId().toString());
            user.setMail(newMail);
            userRepository.save(user);
            cacheUtils.clearCacheRandom(user.getMail());
            cacheUtils.clearCacheNewMail(userId);

            return new Response(GlobalResponses.USER_EMAIL_CHANGED.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.INVALID_CODE.getText());

        }
    }


    private UserModel getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (UserModel) authentication.getPrincipal();
        } else {
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }
    }


    private Response changeMail(String newMail, UserModel userModel) throws IOException {
        String oldMail = userModel.getMail();
        String userId = userModel.getId().toString();

        cacheUtils.clearCacheNewMail(userId);

        cacheUtils.cacheNewMail(newMail, userId);

        cacheUtils.clearCacheRandom(oldMail);

        String newCode = cacheUtils.cacheRandomCode(oldMail);

        sendMailForQueue(newMail,
                userModel.getName(),
                MailTemplate.EMAIL_CHANGED,
                Optional.of(newCode));

        return new Response(GlobalResponses.MAIL_CHANGE_INIT.getText());
    }

    private void sendMailForQueue(String userMail,
                                  String userName,
                                  MailTemplate mailTemplate,
                                  Optional<String> random) throws IOException {

        EmailSendDto emailSendDto = new EmailSendDto(
                userMail,
                mailTemplate.getSubject(),
                mailHtmlGenerator.generatorMailFile(
                        userName,
                        mailTemplate.getText(),
                        random));

        userProducer.publishMessageMail(emailSendDto);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

}
