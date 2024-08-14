package com.homebanking.payments.app.api.accounts.data;

import jakarta.persistence.Embeddable;

@Embeddable
public class Card{

    private String number;
    private CardType type;
    private CardCompany company;
}
