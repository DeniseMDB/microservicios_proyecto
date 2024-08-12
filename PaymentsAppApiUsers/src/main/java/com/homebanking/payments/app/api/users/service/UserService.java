package com.homebanking.payments.app.api.users.service;

import com.homebanking.payments.app.api.users.dto.UserDto;
import com.homebanking.payments.app.api.users.entity.UserEntity;
import com.homebanking.payments.app.api.users.model.AccountsModel;
import com.homebanking.payments.app.api.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    Environment environment;
    RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                       Environment environment, RestTemplate restTemplate){
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity user = modelMapper.map(userDetails, UserEntity.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException("User not found");
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        String accountsUrl = String.format(environment.getProperty("accounts.url"), userId);
        ResponseEntity <AccountsModel> accountsResponse = restTemplate.exchange(accountsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<AccountsModel>() {
        });
        AccountsModel accounts = accountsResponse.getBody();
        userDto.setAccount(accounts);
        return userDto;
    }
}


