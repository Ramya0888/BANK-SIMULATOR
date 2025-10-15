package com.simulator.bank.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceFactory {
    private static volatile HikariDataSource ds;

    private DataSourceFactory() {}

    public static synchronized HikariDataSource getDataSource() {
        if (ds == null) {
            Properties props = new Properties();
            try (var in = DataSourceFactory.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in == null) throw new IllegalStateException("application.properties not found");
                props.load(in);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load application.properties", e);
            }

            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(props.getProperty("db.url"));
            cfg.setUsername(props.getProperty("db.username"));
            cfg.setPassword(props.getProperty("db.password"));
            cfg.setDriverClassName("com.mysql.cj.jdbc.Driver");
            cfg.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.size", "10")));

            ds = new HikariDataSource(cfg);
        }
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
