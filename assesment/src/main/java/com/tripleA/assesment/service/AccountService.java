package com.tripleA.assesment.service;

import com.tripleA.assesment.dto.AccountResponse;
import com.tripleA.assesment.dto.CreateAccountRequest;
import com.tripleA.assesment.dto.TransactionRequest;
import com.tripleA.assesment.model.Account;
import com.tripleA.assesment.model.Transaction;
import com.tripleA.assesment.repository.AccountRepository;
import com.tripleA.assesment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    public void createAccount(CreateAccountRequest request) {
        if (request.getAccountId() == null || request.getInitialBalance() == null) {
            throw new IllegalArgumentException("Account ID and initial balance are required");
        }

        if (accountRepository.existsById(request.getAccountId())) {
            throw new IllegalArgumentException("Account already exists");
        }

        try {
            BigDecimal initialBalance = new BigDecimal(request.getInitialBalance());
            if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }

            Account account = new Account(request.getAccountId(), initialBalance);
            accountRepository.save(account);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid balance format");
        }
    }

    public AccountResponse getAccountBalance(Long accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID is required");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // toPlainString to avoid scientific notation
        return new AccountResponse(account.getAccountId(), account.getBalance().toPlainString());
    }

    @Transactional
    public void processTransaction(TransactionRequest request) {
        if (request.getSourceAccountId() == null || request.getDestinationAccountId() == null || request.getAmount() == null) {
            throw new IllegalArgumentException("Source account ID, destination account ID, and amount are required");
        }

        if (request.getSourceAccountId().equals(request.getDestinationAccountId())) {
            throw new IllegalArgumentException("Source and destination accounts cannot be the same");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(request.getAmount());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Transaction amount must be positive");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }

        Account sourceAccount = accountRepository.findById(request.getSourceAccountId())
            .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        Account destinationAccount = accountRepository.findById(request.getDestinationAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficent balance in sourcee account");
        }

        // Update balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        // TODO: maybe add transaction status tracking later
        Transaction transaction = new Transaction(
            request.getSourceAccountId(),
            request.getDestinationAccountId(),
            amount
        );
        transactionRepository.save(transaction);
    }
}
