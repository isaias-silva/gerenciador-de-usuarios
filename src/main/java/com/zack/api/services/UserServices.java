package com.zack.api.services;

import com.zack.api.dtos.UserCreateDto;
import com.zack.api.dtos.UserLoginDto;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import com.zack.api.util.crypt.Hash;
import com.zack.api.util.responses.Response;
import com.zack.api.util.responses.ResponseJwt;
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

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServices implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;


    public Response registerUser(UserCreateDto userDoc) throws BadRequestException {
        UserModel exists = userRepository.findOneByUsernameOrEmail(userDoc.name(), userDoc.mail());

        if (exists != null) {
            throw new BadRequestException("usuário já existe");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDoc, userModel);
        userModel.setPassword(new Hash().generateHash(userModel.getPassword()));

        ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));

        return new Response("usuário cadastrado com sucesso!");
    }

    public Response loginUser(UserLoginDto userDoc) throws AccountNotFoundException, BadRequestException {
        var userdata = userRepository.findOneByEmail(userDoc.mail());
        if (userdata == null) {
            throw new AccountNotFoundException("usuário não encontrado");
        }
        var correctpass = new Hash().compareHash(userDoc.password(), userdata.getPassword());

        if (!correctpass) {
            throw new BadRequestException("senha incorreta");
        } else {
            String token = tokenService.generateToken(userdata);

            return new ResponseJwt("logado com sucesso", token);
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

            return formatUserData;
        } else {
            throw new AccountNotFoundException("usuário não encontrado");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username);
    }
}
