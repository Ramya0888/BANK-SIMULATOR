package com.simulator.bank.service;

import com.simulator.bank.dao.AccountJdbcDao;
import com.simulator.bank.dao.CustomerDao;
import com.simulator.bank.dao.TransactionDao;
import com.simulator.bank.model.Account;
import com.simulator.bank.model.Customer;
import com.simulator.bank.model.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {

    private final AccountJdbcDao accountDao;
    private final TransactionDao transactionDao;
    private final CustomerDao customerDao;
    private final EmailService emailService;

    public TransactionService(AccountJdbcDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.customerDao = new CustomerDao();
        this.emailService = new EmailService();
    }

    public Transaction deposit(String accNum, BigDecimal amount, String mode) throws SQLException {
        Account acc = accountDao.findByAccountNumber(accNum)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accNum));

        BigDecimal newBalance = acc.getBalance().add(amount);
        accountDao.updateBalance(accNum, newBalance);

        Transaction tx = new Transaction();
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionAmount(amount);
        tx.setReceiverAccountId(acc.getAccountId());
        tx.setReceiverAccountNumber(accNum);
        tx.setBalanceAmount(newBalance);
        tx.setDescription("Deposit");
        tx.setTransactionType("CREDIT");
        tx.setModeOfTransaction(mode);

        transactionDao.save(tx);
        sendEmailNotification(acc.getCustomerId(), "Deposit Successful",
                "₹" + amount + " credited to account " + accNum + "\nNew balance: ₹" + newBalance);
        return tx;
    }


    public Transaction withdraw(String accNum, BigDecimal amount, String mode, Integer pin) throws SQLException {
        Account acc = accountDao.findByAccountNumber(accNum)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accNum));

        validateCustomerPin(acc.getCustomerId(), pin);

        if (acc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in account: " + accNum);
        }

        BigDecimal newBalance = acc.getBalance().subtract(amount);
        accountDao.updateBalance(accNum, newBalance);

        Transaction tx = new Transaction();
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionAmount(amount);
        tx.setSenderAccountId(acc.getAccountId());
        tx.setSenderAccountNumber(accNum);
        tx.setBalanceAmount(newBalance);
        tx.setDescription("Withdrawal");
        tx.setTransactionType("DEBIT");
        tx.setModeOfTransaction(mode);

        transactionDao.save(tx);
        sendEmailNotification(acc.getCustomerId(), "Withdrawal Alert",
                "₹" + amount + " debited from your account " + accNum +
                "\nRemaining balance: ₹" + newBalance);
        return tx;
    }

   
    public Transaction transfer(String fromAccNum, String toAccNum, BigDecimal amount, String mode, Integer pin) throws SQLException {
        Account fromAcc = accountDao.findByAccountNumber(fromAccNum)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found: " + fromAccNum));

        Account toAcc = accountDao.findByAccountNumber(toAccNum)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found: " + toAccNum));

        validateCustomerPin(fromAcc.getCustomerId(), pin); 
        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in source account: " + fromAccNum);
        }

        BigDecimal newFromBalance = fromAcc.getBalance().subtract(amount);
        BigDecimal newToBalance = toAcc.getBalance().add(amount);

        accountDao.updateBalance(fromAccNum, newFromBalance);
        accountDao.updateBalance(toAccNum, newToBalance);

        Transaction tx = new Transaction();
        tx.setTransactionDate(LocalDateTime.now());
        tx.setTransactionAmount(amount);
        tx.setSenderAccountId(fromAcc.getAccountId());
        tx.setSenderAccountNumber(fromAccNum);
        tx.setReceiverAccountId(toAcc.getAccountId());
        tx.setReceiverAccountNumber(toAccNum);
        tx.setBalanceAmount(newFromBalance);
        tx.setDescription("Transfer to " + toAccNum);
        tx.setTransactionType("TRANSFER");
        tx.setModeOfTransaction(mode);

        transactionDao.save(tx);
        sendEmailNotification(fromAcc.getCustomerId(), "Transfer Sent",
                "₹" + amount + " transferred to account " + toAccNum +
                "\nRemaining balance: ₹" + newFromBalance);
        sendEmailNotification(toAcc.getCustomerId(), "Transfer Received",
                "₹" + amount + " received from account " + fromAccNum +
                "\nNew balance: ₹" + newToBalance);
        return tx;
    }


    private void validateCustomerPin(Integer customerId, Integer enteredPin) throws SQLException {
        Customer customer = customerDao.findById(customerId);
        if (customer == null)
            throw new IllegalArgumentException("Customer not found for ID: " + customerId);
        if (customer.getPin() == null)
            throw new IllegalArgumentException("PIN not set for this customer");
        if (!customer.getPin().equals(enteredPin))
            throw new IllegalArgumentException("Invalid PIN entered");
    }

    public BigDecimal getBalance(String accNum) throws SQLException {
        Account acc = accountDao.findByAccountNumber(accNum)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accNum));
        return acc.getBalance();
    }

    public List<Transaction> getTransactions(String accNum) throws SQLException {
        return transactionDao.findByAccountNumber(accNum);
    }

    private void sendEmailNotification(int customerId, String subject, String message) {
        try {
            Customer customer = customerDao.findById(customerId);
            if (customer != null && customer.getEmail() != null && !customer.getEmail().isBlank()) {
                emailService.sendEmail(customer.getEmail(), subject, message);
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
