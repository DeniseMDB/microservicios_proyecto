package com.homebanking.payments.app.api.accounts.models;

import com.homebanking.payments.app.api.accounts.data.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter@Setter
public class PaymentRequestModel {
    @NotNull(message="Amount cannot be empty")
    private BigDecimal amount;

    @NotNull
    @Size(min = 10, max = 10, message = "The code must be 10 digits long")
    private String digitalPaymentCode;

    @NotNull(message="You must choose a payment method")
    private PaymentMethod paymentMethod;
}
