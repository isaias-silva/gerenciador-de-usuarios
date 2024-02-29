package com.zack.api.services;


import com.zack.api.dtos.UserCreateDto;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import util.Response;

@Service("user service")
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public Response registerUser(UserCreateDto userDoc) throws BadRequestException {
        UserModel exists = userRepository.findOneByUsernameOrEmail(userDoc.name(), userDoc.mail());

        if(exists != null) {
                throw new BadRequestException("usuário já existe");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDoc, userModel);
        ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
        return new Response("usuário cadastrado com sucesso!");
    }
}
