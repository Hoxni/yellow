package com.example.yellow.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:latest")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("pass");

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        postgreSQLContainer.start();
        TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
