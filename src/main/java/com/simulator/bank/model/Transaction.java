package com.simulator.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer transactionId;
    private LocalDateTime transactionDate;
    private BigDecimal transactionAmount;

    private Integer senderAccountId;
    private String senderAccountNumber;

    private Integer receiverAccountId;
    private String receiverAccountNumber;

    private BigDecimal balanceAmount;
    private String description;
    private String transactionType; // DEPOSIT, WITHDRAW, TRANSFER
    private String modeOfTransaction;


    public Integer getTransactionId() { return transactionId; }
    public void setTransactionId(Integer transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public BigDecimal getTransactionAmount() { return transactionAmount; }
    public void setTransactionAmount(BigDecimal transactionAmount) { this.transactionAmount = transactionAmount; }

    public Integer getSenderAccountId() { return senderAccountId; }
    public void setSenderAccountId(Integer senderAccountId) { this.senderAccountId = senderAccountId; }

    public String getSenderAccountNumber() { return senderAccountNumber; }
    public void setSenderAccountNumber(String senderAccountNumber) { this.senderAccountNumber = senderAccountNumber; }

    public Integer getReceiverAccountId() { return receiverAccountId; }
    public void setReceiverAccountId(Integer receiverAccountId) { this.receiverAccountId = receiverAccountId; }

    public String getReceiverAccountNumber() { return receiverAccountNumber; }
    public void setReceiverAccountNumber(String receiverAccountNumber) { this.receiverAccountNumber = receiverAccountNumber; }

    public BigDecimal getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(BigDecimal balanceAmount) { this.balanceAmount = balanceAmount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getModeOfTransaction() { return modeOfTransaction; }
    public void setModeOfTransaction(String modeOfTransaction) { this.modeOfTransaction = modeOfTransaction; }
}
