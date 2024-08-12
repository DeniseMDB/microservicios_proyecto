package com.homebanking.payments.app.api.accounts.controllers;

import com.homebanking.payments.app.api.accounts.dto.AccountDTO;
import com.homebanking.payments.app.api.accounts.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/{id}/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public AccountDTO getUserAccounts(@PathVariable String id){
        AccountDTO accountDTO = accountService.assignAccountsToUser(id);
        logger.info("Returning account for user: "+id);
        return accountDTO;
    }
}
