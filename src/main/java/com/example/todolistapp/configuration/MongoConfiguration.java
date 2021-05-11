package com.example.todolistapp.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.database}")
    private String password;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                                                                     .applyConnectionString(buildConnectionString())
                                                                     .build();
        return MongoClients.create(mongoClientSettings);
    }

    private ConnectionString buildConnectionString() {
        return new ConnectionString("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + databaseName);
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
}
