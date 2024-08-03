package com.homebanking.payments.app.api.users.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserResponseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}
