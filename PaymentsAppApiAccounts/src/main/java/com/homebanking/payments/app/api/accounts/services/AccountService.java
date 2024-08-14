package com.homebanking.payments.app.api.accounts.services;

import com.homebanking.payments.app.api.accounts.dto.AccountDTO;
import com.homebanking.payments.app.api.accounts.entities.Account;
import com.homebanking.payments.app.api.accounts.repositories.AccountRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private final Random random = new Random();
    private final ModelMapper modelMapper;

    public AccountService() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public AccountDTO assignAccountsToUser(String userId) {
        Optional<Account> optionalArsAccount = accountRepository.findByUserIdAndCurrency(userId, "ARS");

        Account arsAccount = optionalArsAccount.orElseGet(() -> createRandomAccount(userId, "ARS"));
        return convertToDto(arsAccount);
    }

    private Account createRandomAccount(String userId, String currency) {
        Account account = new Account();
        account.setAccountNumber(generateRandomAccountNumber());
        account.setCurrency(currency);
        account.setUserId(userId);
        account.setBalance(generateRandomBalance());

        return accountRepository.save(account);
    }

    private BigDecimal generateRandomBalance() {
        double randomBalance = 10000 + (100000 - 10000) * random.nextDouble();
        return BigDecimal.valueOf(randomBalance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private String generateRandomAccountNumber(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private AccountDTO convertToDto(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }


    public void deductAmount(String accountId, BigDecimal amount) throws Exception {
        Optional<Account> accountOptional = accountRepository.findByAccountId(accountId);
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
        }else{
            throw new Exception("Account does not exist");
        }
    }
}
