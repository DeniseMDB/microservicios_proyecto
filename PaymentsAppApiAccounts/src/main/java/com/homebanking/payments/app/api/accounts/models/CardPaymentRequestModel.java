package com.homebanking.payments.app.api.accounts.models;

import com.homebanking.payments.app.api.accounts.data.CardCompany;
import com.homebanking.payments.app.api.accounts.data.CardType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CardPaymentRequestModel extends PaymentRequestModel {

    @NotNull(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Card type is required")
    private CardType cardType;

    @NotNull(message = "Card company is required")
    private CardCompany cardCompany;

    // Getters and setters
}
