package com.homebanking.payments.app.api.accounts.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AccountPaymentRequestModel extends PaymentRequestModel {

    @NotNull(message = "Account ID is required")
    private String accountId;

}