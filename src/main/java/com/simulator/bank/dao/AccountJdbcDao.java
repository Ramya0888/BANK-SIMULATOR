package com.simulator.bank.dao;

import com.simulator.bank.model.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountJdbcDao implements AccountDAO {

    private final DataSource dataSource;

    public AccountJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Account> listAll() throws SQLException {
        List<Account> out = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                out.add(mapRow(rs));
            }
        }
        return out;
    }

    @Override
    public Optional<Account> findByAccountNumber(String accNum) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accNum);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account acc = mapRow(rs);
                    System.out.println("Account found in DB: " + acc.getAccountNumber() +
                            " | Balance: " + acc.getBalance());
                    return Optional.of(acc);
                } else {
                    System.out.println("No account found for account_number: " + accNum);
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Account> findByCustomer(int customerId) throws SQLException {
        List<Account> out = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    @Override
    public int create(Account a) throws SQLException {
        String sql = "INSERT INTO accounts " +
                "(customer_id, account_type, bank_name, branch, balance, status, " +
                "account_number, ifsc_code, name_on_account, phone_linked, saving_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setStatementForInsert(ps, a);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    a.setAccountId(id);
                    return id;
                }
            }
            return -1;
        }
    }

    @Override
    public boolean updateBalance(String accNum, BigDecimal bal) throws SQLException {
        String sql = "UPDATE accounts SET balance=? WHERE account_number=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, bal);
            ps.setString(2, accNum);

            int updated = ps.executeUpdate();
            System.out.println(" Balance updated for " + accNum + " -> " + bal + " (rows: " + updated + ")");
            return updated == 1;
        }
    }

    @Override
    public boolean updateAccount(String accNum, Account a) throws SQLException {
        String sql = "UPDATE accounts SET " +
                "customer_id=?, account_type=?, bank_name=?, branch=?, balance=?, " +
                "status=?, ifsc_code=?, name_on_account=?, phone_linked=?, saving_amount=? " +
                "WHERE account_number=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getCustomerId());
            ps.setString(2, a.getAccountType());
            ps.setString(3, a.getBankName());
            ps.setString(4, a.getBranch());
            ps.setBigDecimal(5, a.getBalance());
            ps.setString(6, a.getStatus());
            ps.setString(7, a.getIfscCode());
            ps.setString(8, a.getNameOnAccount());
            ps.setString(9, a.getPhoneLinked());
            ps.setBigDecimal(10, a.getSavingAmount());
            ps.setString(11, accNum);

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean deleteByAccountNumber(String accNum) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accNum);
            return ps.executeUpdate() == 1;
        }
    }

    // ---------- helpers ----------

    private void setStatementForInsert(PreparedStatement ps, Account a) throws SQLException {
        ps.setInt(1, a.getCustomerId());
        ps.setString(2, a.getAccountType());
        ps.setString(3, a.getBankName());
        ps.setString(4, a.getBranch());
        ps.setBigDecimal(5, a.getBalance());
        ps.setString(6, a.getStatus());
        ps.setString(7, a.getAccountNumber());
        ps.setString(8, a.getIfscCode());
        ps.setString(9, a.getNameOnAccount());
        ps.setString(10, a.getPhoneLinked());
        ps.setBigDecimal(11, a.getSavingAmount());
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setAccountId(rs.getInt("account_id"));
        acc.setCustomerId(rs.getInt("customer_id"));
        acc.setAccountType(rs.getString("account_type"));
        acc.setBankName(rs.getString("bank_name"));
        acc.setBranch(rs.getString("branch"));
        acc.setBalance(rs.getBigDecimal("balance"));
        acc.setStatus(rs.getString("status"));
        acc.setAccountNumber(rs.getString("account_number"));
        acc.setIfscCode(rs.getString("ifsc_code"));
        acc.setNameOnAccount(rs.getString("name_on_account"));
        acc.setPhoneLinked(rs.getString("phone_linked"));
        acc.setSavingAmount(rs.getBigDecimal("saving_amount"));
        return acc;
    }
}
