package com.homebanking.payments.app.api.accounts.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "accounts")
@NoArgsConstructor
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 5562041478278499940L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String userId;  // userId del usuario en el otro microservicio

}