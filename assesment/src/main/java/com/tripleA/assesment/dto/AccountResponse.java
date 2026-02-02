package com.tripleA.assesment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountResponse {
    @JsonProperty("account_id")
    private Long accountId;
    
    private String balance;

    public AccountResponse() {
    }

    public AccountResponse(Long accountId, String balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
