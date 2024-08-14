package com.homebanking.payments.app.api.accounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account_payments")
@Data
public class AccountPayment extends Payment{

    @Column(nullable = false)
    private String accountId;
}
