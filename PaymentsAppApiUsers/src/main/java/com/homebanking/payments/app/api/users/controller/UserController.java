package com.homebanking.payments.app.api.users.controller;

import com.homebanking.payments.app.api.users.dto.UserDto;
import com.homebanking.payments.app.api.users.model.UserRequestModel;
import com.homebanking.payments.app.api.users.model.UserResponseModel;
import com.homebanking.payments.app.api.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "Wroking...";
    }

    @PostMapping
    public ResponseEntity<UserResponseModel> createUser(@RequestBody UserRequestModel userDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        UserResponseModel returnValue = modelMapper.map(createdUser, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }
    // quede en que no se si crear el modelo de user request o no 7.30


}