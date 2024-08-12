package com.homebanking.payments.app.api.users.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class AccountsModel {
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
}
