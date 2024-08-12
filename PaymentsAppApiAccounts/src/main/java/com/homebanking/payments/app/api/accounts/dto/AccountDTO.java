package com.homebanking.payments.app.api.accounts.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6145085448243055643L;

    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String userId;
}
