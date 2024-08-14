package com.homebanking.payments.app.api.accounts.entities;

import com.homebanking.payments.app.api.accounts.data.Card;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "card_payments")
@Data
public class CardPayment extends Payment{

    @Embedded
    private Card cardDetails;
}
