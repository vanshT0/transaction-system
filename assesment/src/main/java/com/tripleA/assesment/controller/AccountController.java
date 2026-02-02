package com.tripleA.assesment.controller;

import com.tripleA.assesment.dto.AccountResponse;
import com.tripleA.assesment.dto.CreateAccountRequest;
import com.tripleA.assesment.dto.TransactionRequest;
import com.tripleA.assesment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts/{account_id}")
    public ResponseEntity<AccountResponse> getAccountBalance(@PathVariable("account_id") Long accountId) {
        AccountResponse response = accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> processTransaction(@RequestBody TransactionRequest request) {
        accountService.processTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
