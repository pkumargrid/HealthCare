package com.healthcare.system.configuration;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
@PropertySource(value = "classpath:prod.yml", ignoreResourceNotFound = true)
public class DatabaseConfig {

    private final Environment environment;

    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
        hikariConfig.setUsername(environment.getProperty("spring.datasource.password"));
        hikariConfig.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        return hikariConfig.getDataSource();
    }
}
