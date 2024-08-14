package com.homebanking.payments.app.api.accounts.repositories;

import com.homebanking.payments.app.api.accounts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserIdAndCurrency(String userId, String currency);
    Optional<Account> findByAccountId(String accountId);
}
