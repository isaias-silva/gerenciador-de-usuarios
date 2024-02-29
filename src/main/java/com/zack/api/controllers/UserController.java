package com.zack.api.controllers;


import com.zack.api.dtos.UserCreateDto;
import com.zack.api.models.UserModel;
import com.zack.api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody @Valid UserCreateDto userCreateRecordDto){
    var userModel = new UserModel();
    BeanUtils.copyProperties(userCreateRecordDto,userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
    }
}
