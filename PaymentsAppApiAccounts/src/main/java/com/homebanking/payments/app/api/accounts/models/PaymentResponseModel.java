package com.homebanking.payments.app.api.accounts.models;

import com.homebanking.payments.app.api.accounts.data.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class PaymentResponseModel {
    private String digitalPaymentCode;
    private Double amount;
    private LocalDateTime dateTime;
    private PaymentMethod paymentMethod;
}
