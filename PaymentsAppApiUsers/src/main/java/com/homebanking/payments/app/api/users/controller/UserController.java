package com.homebanking.payments.app.api.users.controller;

import com.homebanking.payments.app.api.users.dto.UserDto;
import com.homebanking.payments.app.api.users.model.UserRequestModel;
import com.homebanking.payments.app.api.users.model.UserResponseModel;
import com.homebanking.payments.app.api.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private Environment env;
//    @Autowired
//    RestTemplate restTemplate;

    @GetMapping("/status/check")
        public String status()
        {
            return "Working on port " + env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret");
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
}