package com.homebanking.payments.app.api.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebanking.payments.app.api.users.dto.UserDto;
import com.homebanking.payments.app.api.users.model.LoginRequestModel;
import com.homebanking.payments.app.api.users.service.IUserService;
import com.homebanking.payments.app.api.users.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final IUserService userService;
    private final Environment environment;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthenticationFilter(IUserService userService, Environment environment,
                                AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.environment = environment;
        this.jwtTokenGenerator = jwtTokenGenerator;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(userName);

        String token = jwtTokenGenerator.generateToken(userDetails.getUserId());

        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }
}
