package com.simulator.bank.app;

import com.simulator.bank.config.DataSourceFactory;

import com.simulator.bank.dao.AccountDAO;
import com.simulator.bank.dao.AccountJdbcDao;
import com.simulator.bank.model.Account;
import com.simulator.bank.service.AccountService;
import com.simulator.bank.service.impl.AccountServiceImpl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws Exception {
        //DataSource ds = DataSourceFactory.getDataSource();
        var ds = DataSourceFactory.getDataSource();
        //DatabaseInitializer.initialize(ds);
        AccountDAO dao = new AccountJdbcDao(ds);
        AccountService service = new AccountServiceImpl(dao);

        System.out.println("=== Bank Simulator CLI demo ===");

        // 1. create
        Account a1 = new Account();
        a1.setAccountNumber("ACC-" + (System.currentTimeMillis() % 100000));
        a1.setNameOnAccount("Ramya");
        a1.setBalance(new BigDecimal("1500.00"));
        a1.setAccountType("SAVINGS");
        a1.setCustomerId(1); // set a sample existing customer id
        a1.setBankName("SimuBank");
        a1.setIfscCode("SIMU0001234");  // ✅ Add a sample valid IFSC code
        a1.setPhoneLinked("9876543210"); // ✅ Added phone number
        a1.setStatus("active");



        service.createAccount(a1);
        System.out.println("Created: " + a1);

        // 2. fetch
        Optional<Account> fetched = service.findByAccountNumber(a1.getAccountNumber());
        System.out.println("Fetched: " + fetched.orElse(null));

        // 3. update
        fetched.ifPresent(acc -> {
            try {
                acc.setBalance(acc.getBalance().add(new BigDecimal("500.00")));
                boolean ok = service.updateAccount(acc);
                System.out.println("Updated: " + ok + " => " + acc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 4. list
        List<Account> all = service.listAll();
        System.out.println("All accounts (" + all.size() + "):");
        all.forEach(System.out::println);

        // 5. delete
        boolean deleted = service.deleteByAccountNumber(a1.getAccountNumber());
        System.out.println("Deleted account " + a1.getAccountNumber() + ": " + deleted);
    }
}
