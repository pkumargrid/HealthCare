package com.healthcare.system.database;

import com.zaxxer.hikari.HikariConfig;

import javax.sql.DataSource;

public class DatabaseConnection {
    public DataSource setUp(String url, String username, String password, HikariConfig hikariConfig) {
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName("org.postgres.Driver");
        hikariConfig.setPassword(password);
        hikariConfig.setUsername(username);
        return hikariConfig.getDataSource();
    }
}
