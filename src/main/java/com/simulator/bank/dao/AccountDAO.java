package com.simulator.bank.dao;

import com.simulator.bank.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountDAO {

    List<Account> listAll() throws SQLException;

    Optional<Account> findByAccountNumber(String accNum) throws SQLException;

    List<Account> findByCustomer(int customerId) throws SQLException;

    int create(Account a) throws SQLException;

    boolean updateBalance(String accNum, BigDecimal bal) throws SQLException;

    boolean updateAccount(String accNum, Account a) throws SQLException;

    boolean deleteByAccountNumber(String accNum) throws SQLException;
}
