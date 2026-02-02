package com.tripleA.assesment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountRequest {
    @JsonProperty("account_id")
    private Long accountId;
    
    @JsonProperty("initial_balance")
    private String initialBalance;

    public CreateAccountRequest() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(String initialBalance) {
        this.initialBalance = initialBalance;
    }
}
