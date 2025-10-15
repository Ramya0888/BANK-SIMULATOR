package com.simulator.bank.dao;

import com.simulator.bank.config.DataSourceFactory;
import com.simulator.bank.model.Transaction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    
    public int save(Transaction tx) throws SQLException {
        String sql = """
            INSERT INTO transactions (
                transaction_date,
                transaction_amount,
                sender_account_id,
                sender_account_number,
                receiver_account_id,
                receiver_account_number,
                balance_amount,
                description,
                transaction_type,
                mode_of_transaction
            ) VALUES (?,?,?,?,?,?,?,?,?,?)
        """;

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

           
            if (tx.getTransactionDate() == null) {
                tx.setTransactionDate(LocalDateTime.now());
            }

            ps.setTimestamp(1, Timestamp.valueOf(tx.getTransactionDate()));
            ps.setBigDecimal(2, tx.getTransactionAmount());
            ps.setObject(3, tx.getSenderAccountId(), Types.INTEGER);
            ps.setString(4, tx.getSenderAccountNumber());
            ps.setObject(5, tx.getReceiverAccountId(), Types.INTEGER);
            ps.setString(6, tx.getReceiverAccountNumber());
            ps.setBigDecimal(7, tx.getBalanceAmount());
            ps.setString(8, tx.getDescription());
            ps.setString(9, tx.getTransactionType());
            ps.setString(10, tx.getModeOfTransaction());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    tx.setTransactionId(id);
                    return id;
                }
            }
        }

        throw new SQLException("Failed to insert transaction record");
    }

   
    public List<Transaction> findByAccountNumber(String accountNumber) throws SQLException {
        String sql = """
            SELECT * FROM transactions
            WHERE sender_account_number = ? OR receiver_account_number = ?
            ORDER BY transaction_date DESC
        """;

        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ps.setString(2, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTransaction(rs));
                }
            }
        }

        return list;
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        Transaction tx = new Transaction();

        tx.setTransactionId(rs.getInt("transaction_id"));
        tx.setTransactionAmount(rs.getBigDecimal("transaction_amount"));
        tx.setSenderAccountId(rs.getObject("sender_account_id", Integer.class));
        tx.setSenderAccountNumber(rs.getString("sender_account_number"));
        tx.setReceiverAccountId(rs.getObject("receiver_account_id", Integer.class));
        tx.setReceiverAccountNumber(rs.getString("receiver_account_number"));
        tx.setBalanceAmount(rs.getBigDecimal("balance_amount"));
        tx.setDescription(rs.getString("description"));
        tx.setTransactionType(rs.getString("transaction_type"));
        tx.setModeOfTransaction(rs.getString("mode_of_transaction"));

   
        Timestamp ts = rs.getTimestamp("transaction_date");
        if (ts != null) {
            tx.setTransactionDate(ts.toLocalDateTime());
        } else {
            
            tx.setTransactionDate(LocalDateTime.now());
        }

        return tx;
    }
}