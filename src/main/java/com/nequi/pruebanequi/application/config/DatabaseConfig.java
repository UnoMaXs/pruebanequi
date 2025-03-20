package com.nequi.pruebanequi.application.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.core.DatabaseClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner initDatabase(DatabaseClient databaseClient) {
        return args -> {
            String sqlScript = new BufferedReader(
                    new InputStreamReader(new ClassPathResource("schema.sql").getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));

            databaseClient.sql(sqlScript)
                    .fetch()
                    .rowsUpdated()
                    .then()
                    .block();
        };
    }
}