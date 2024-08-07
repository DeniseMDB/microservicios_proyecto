package com.homebanking.payments.app.api.users.security;

import com.homebanking.payments.app.api.users.service.IUserService;
import com.homebanking.payments.app.api.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurity {
    @Autowired
    Environment environment;
    @Autowired
    IUserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenGenerator jwtTokenGenerator;

    public WebSecurity(Environment environment, IUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenGenerator jwtTokenGenerator) {
        this.environment = environment;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // Create AuthenticationFilter
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(userService, environment, authenticationManager, jwtTokenGenerator);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http
                .cors(cors -> {
                })
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz.requestMatchers(new AntPathRequestMatcher("/users/**")).access
                                (new WebExpressionAuthorizationManager(
                                "hasIpAddress('" + environment.getProperty("gateway.ip") + "')"))
                        .requestMatchers(HttpMethod.POST, environment.getProperty("login.url.path")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .anyRequest()
                        .authenticated())
                        .addFilter(authenticationFilter)
                        .authenticationManager(authenticationManager)
                        .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));
        return http.build();

    }
}
