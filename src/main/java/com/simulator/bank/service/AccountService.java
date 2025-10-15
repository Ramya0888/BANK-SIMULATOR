package com.simulator.bank.service;



import com.simulator.bank.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account) throws Exception;
    Optional<Account> findByAccountNumber(String accountNumber) throws Exception;
    List<Account> listAll() throws Exception;
    boolean updateAccount(Account account) throws Exception;
    boolean deleteByAccountNumber(String accountNumber) throws Exception;
}

