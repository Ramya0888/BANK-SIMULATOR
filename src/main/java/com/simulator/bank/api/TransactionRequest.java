package com.simulator.bank.api;

import java.math.BigDecimal;

public class TransactionRequest {
    private String accountNumber;   // for deposit/withdraw
    private String fromAccount;     // for transfer
    private String toAccount;       // for transfer
    private BigDecimal amount;
    private String description;
    private String mode;

 
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getFromAccount() { return fromAccount; }
    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }

    public String getToAccount() { return toAccount; }
    public void setToAccount(String toAccount) { this.toAccount = toAccount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
}
