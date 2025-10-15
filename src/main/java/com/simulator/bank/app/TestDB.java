package com.simulator.bank.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.simulator.bank.util.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println(" Connected to database!");

            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                System.out.println(id + " | " + name + " | " + balance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
