package com.homebanking.payments.app.api.users.dto;

import com.homebanking.payments.app.api.users.model.AccountsModel;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6789746875324635444L;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String userId;
    private String encryptedPassword;
    private AccountsModel account;
}
