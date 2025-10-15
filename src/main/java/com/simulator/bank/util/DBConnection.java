package com.simulator.bank.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static String url;
    private static String user;
    private static String pass;

    static {
        try (InputStream in = DBConnection.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            Properties p = new Properties();
            p.load(in);
            url = p.getProperty("db.url");
            user = p.getProperty("db.username");
            pass = p.getProperty("db.password");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load DB properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, pass);
    }
}
