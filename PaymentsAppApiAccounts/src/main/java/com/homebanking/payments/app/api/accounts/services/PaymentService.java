package com.homebanking.payments.app.api.accounts.services;

import com.homebanking.payments.app.api.accounts.data.PaymentMethod;
import com.homebanking.payments.app.api.accounts.entities.AccountPayment;
import com.homebanking.payments.app.api.accounts.entities.CardPayment;
import com.homebanking.payments.app.api.accounts.entities.Payment;
import com.homebanking.payments.app.api.accounts.models.AccountPaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.CardPaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.PaymentRequestModel;
import com.homebanking.payments.app.api.accounts.models.PaymentResponseModel;
import com.homebanking.payments.app.api.accounts.repositories.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AccountService accountService;


    public PaymentResponseModel createCardPayment(CardPaymentRequestModel paymentDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CardPayment newPayment = modelMapper.map(paymentDetails, CardPayment.class);
        newPayment.setPaymentMethod(PaymentMethod.CARD);
        newPayment.setDateTime(LocalDateTime.now());
        paymentRepository.save(newPayment);
        return modelMapper.map(newPayment, PaymentResponseModel.class);
    }

    public PaymentResponseModel createAccountPayment(AccountPaymentRequestModel paymentDetails) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AccountPayment newPayment = modelMapper.map(paymentDetails, AccountPayment.class);
        newPayment.setPaymentMethod(PaymentMethod.ACCOUNT);
        newPayment.setDateTime(LocalDateTime.now());
        accountService.deductAmount(paymentDetails.getAccountId(), paymentDetails.getAmount());
        paymentRepository.save(newPayment);
        return modelMapper.map(newPayment, PaymentResponseModel.class);
    }
}
