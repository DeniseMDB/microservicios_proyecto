package com.homebanking.payments.app.api.accounts.repositories;

import com.homebanking.payments.app.api.accounts.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
