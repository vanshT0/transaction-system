package com.tripleA.assesment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionRequest {
    @JsonProperty("source_account_id")
    private Long sourceAccountId;
    
    @JsonProperty("destination_account_id")
    private Long destinationAccountId;
    
    private String amount;

    public TransactionRequest() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }
}
