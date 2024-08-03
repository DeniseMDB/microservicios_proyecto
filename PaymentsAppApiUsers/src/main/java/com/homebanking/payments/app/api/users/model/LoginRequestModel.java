package com.homebanking.payments.app.api.users.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LoginRequestModel {
    private String email;
    private String password;
}
