package com.homebanking.payments.app.api.accounts.controllers;


import com.homebanking.payments.app.api.accounts.models.AccountPaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.CardPaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.PaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.PaymentResponseModel;
import com.homebanking.payments.app.api.accounts.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{id}/payments")
public class PaymentController {

    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/card")
    public ResponseEntity<PaymentResponseModel> registerCardPayment(@RequestBody CardPaymentRequestModel paymentDetails) {
        PaymentResponseModel paymentResponse = paymentService.createCardPayment(paymentDetails);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }

    @PostMapping("/account")
    public ResponseEntity<PaymentResponseModel> registerAccountPayment(@RequestBody AccountPaymentRequestModel paymentDetails) throws Exception {
        PaymentResponseModel paymentResponse = paymentService.createAccountPayment(paymentDetails);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponse);
    }
}
