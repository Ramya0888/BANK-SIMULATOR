package com.simulator.bank.service.impl;

import com.simulator.bank.dao.AccountDAO;
import com.simulator.bank.model.Account;
import com.simulator.bank.service.AccountService;
import com.simulator.bank.validation.AccountValidator;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account createAccount(Account account) throws Exception {
        // ✅ Validate account fields
        AccountValidator.validate(account);

        // ✅ Auto-generate account number if null
        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            String generatedNumber = "ACC" + System.currentTimeMillis();
            account.setAccountNumber(generatedNumber);
        }

        accountDAO.create(account);
        return account;
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) throws Exception {
        return accountDAO.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> listAll() throws Exception {
        return accountDAO.listAll();
    }

    @Override
    public boolean updateAccount(Account account) throws Exception {
        // ✅ Validate account fields before update
        AccountValidator.validate(account);

        return accountDAO.updateAccount(account.getAccountNumber(), account);
    }

    @Override
    public boolean deleteByAccountNumber(String accountNumber) throws Exception {
        return accountDAO.deleteByAccountNumber(accountNumber);
    }
}
