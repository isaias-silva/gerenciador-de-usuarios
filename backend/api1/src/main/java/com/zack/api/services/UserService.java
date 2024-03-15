package com.zack.api.services;

import com.zack.api.components.crypt.Hash;
import com.zack.api.components.files.MailHtmlGenerator;
import com.zack.api.components.utils.CacheUtils;
import com.zack.api.dtos.*;
import com.zack.api.infra.queue.producers.MailProducer;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.roles.UserRole;
import com.zack.api.util.exceptions.ForbiddenException;
import com.zack.api.util.exceptions.NotFoundException;
import com.zack.api.util.mail.MailTemplate;
import com.zack.api.util.responses.bodies.Response;
import com.zack.api.util.responses.bodies.ResponseJwt;
import com.zack.api.util.responses.bodies.UserData;

import com.zack.api.util.responses.enums.GlobalResponses;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


@Service("userService")
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    MailProducer userProducer;
    @Autowired
    Hash hash;
    @Autowired
    CacheUtils cacheUtils;
    @Autowired
    MailHtmlGenerator mailHtmlGenerator;

    @Transactional

    public ResponseJwt registerUser(UserCreateDto userDoc) throws IOException {

        UserModel exists = userRepository.findOneByUsernameOrEmail(userDoc.name(), userDoc.mail());
        if (exists != null) {
            throw new BadRequestException(GlobalResponses.USER_ALREADY_EXISTS.getText());
        }

        sendMailForQueue(userDoc.mail(), userDoc.name(), MailTemplate.CODE_VERIFICATION, Optional.of(cacheUtils.cacheRandomCode(userDoc.mail())));
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDoc, userModel);
        userModel.setRole(UserRole.VERIFY_MAIL);
        userModel.setPassword(hash.generateHash(userModel.getPassword()));
        userRepository.save(userModel);
        String token = tokenService.generateToken(userModel);
        return new ResponseJwt(GlobalResponses.USER_REGISTERED.getText(), token);
    }

    public ResponseJwt loginUser(UserLoginDto userDoc) throws BadRequestException {

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

    public UserData getMe() {
        UserModel user = getAuth();
        return new UserData(user);
    }

    public Response updateUser(UserUpdateDto data) throws IOException {

        UserModel user = getAuth();
        Optional<String> name = data.name() != null ? Optional.of(data.name()) : Optional.empty();
        Optional<String> resume = data.resume() != null ? Optional.of(data.resume()) : Optional.empty();
        Optional<String> githubURL = data.githubURL() != null ? Optional.of(data.githubURL()) : Optional.empty();
        Optional<String> instagramURL = data.instagramURL() != null ? Optional.of(data.instagramURL()) : Optional.empty();
        Optional<String> portfolioURL = data.portfolioURL() != null ? Optional.of(data.portfolioURL()) : Optional.empty();

        name.ifPresent(user::setName);
        resume.ifPresent(user::setResume);
        githubURL.ifPresent(user::setGithubURL);
        instagramURL.ifPresent(user::setInstagramURL);
        portfolioURL.ifPresent(user::setPortfolioURL);

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

            sendMailForQueue(user.getMail(), user.getName(), MailTemplate.EMAIL_VALIDATED, Optional.empty());
            return new Response(GlobalResponses.MAIL_VALIDATED.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.INVALID_CODE.getText());
        }

    }

    public Response sendNewCode() throws IOException {

        UserModel user = getAuth();
        if (user.getRole() != UserRole.VERIFY_MAIL) {
            throw new BadRequestException(GlobalResponses.MAIL_ALREADY_VALIDATED.getText());
        }
        cacheUtils.clearCacheRandom(user.getMail());

        String newCode = cacheUtils.getCodeFromCache(user.getMail());

        sendMailForQueue(user.getMail(), user.getName(), MailTemplate.NEW_CODE_VERIFICATION, Optional.of(newCode));

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

    public UserModel getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return (UserModel) authentication.getPrincipal();
        } else {
            throw new ForbiddenException(GlobalResponses.USER_FORBIDDEN.getText());
        }
    }

    public Response changePassword(ChangePasswordDto changePasswordDto) {
        UserModel user = getAuth();
        boolean correctPassword = hash.compareHash(changePasswordDto.password(), user.getPassword());
        if (!correctPassword) {
            throw new ForbiddenException(GlobalResponses.USER_INCORRECT_PASSWORD.getText());
        } else {
            user.setPassword(hash.generateHash(changePasswordDto.newPassword()));
            userRepository.save(user);
            return new Response(GlobalResponses.USER_UPDATED.getText());
        }

    }

    public Response forgottenPassword(RedefinePasswordDto redefinePasswordDto) throws IOException {
        UserModel user = userRepository.findOneByEmail(redefinePasswordDto.email());
        if (user == null) {
            throw new NotFoundException(GlobalResponses.USER_NOT_FOUND.getText());
        }

        cacheUtils.clearCacheRandom(user.getId().toString());
        String code = cacheUtils.cacheRandomCode(user.getId().toString());
        sendMailForQueue(user.getMail(), user.getName(), MailTemplate.PASSWORD_FORGOTTEN, Optional.of(code));
        return new Response(GlobalResponses.PASSWORD_CHANGE_INIT.getText());
    }

    public Response changeForgottenPassword(ChangePasswordForgottenDto changePasswordForgottenDto) throws IOException {
        UserModel user = userRepository.findOneByEmail(changePasswordForgottenDto.mail());
        if (user == null) {
            throw new NotFoundException(GlobalResponses.USER_NOT_FOUND.getText());
        }
        String cacheCode = cacheUtils.getCodeFromCache(user.getId().toString());
        System.out.println(cacheCode);
        if (Objects.equals(cacheCode, changePasswordForgottenDto.code())) {
            user.setPassword(hash.generateHash(changePasswordForgottenDto.newPassword()));
            userRepository.save(user);
            cacheUtils.clearCacheRandom(user.getId().toString());
            return new Response(GlobalResponses.USER_UPDATED.getText());
        } else {
            throw new ForbiddenException(GlobalResponses.INVALID_CODE.getText());
        }
    }

    private Response changeMail(String newMail, UserModel userModel) throws IOException {
        String oldMail = userModel.getMail();
        String userId = userModel.getId().toString();

        cacheUtils.clearCacheNewMail(userId);

        cacheUtils.cacheNewMail(newMail, userId);

        cacheUtils.clearCacheRandom(oldMail);

        String newCode = cacheUtils.cacheRandomCode(oldMail);

        sendMailForQueue(newMail, userModel.getName(), MailTemplate.EMAIL_CHANGED, Optional.of(newCode));

        return new Response(GlobalResponses.MAIL_CHANGE_INIT.getText());
    }

    private void sendMailForQueue(String userMail, String userName, MailTemplate mailTemplate, Optional<String> random) throws IOException {

        EmailSendDto emailSendDto = new EmailSendDto(userMail, mailTemplate.getSubject(), mailHtmlGenerator.generatorMailFile(userName, mailTemplate.getText(), random));

        userProducer.publishMessageMail(emailSendDto);

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }

}
