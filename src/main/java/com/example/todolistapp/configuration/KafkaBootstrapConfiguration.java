package com.example.todolistapp.configuration;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class KafkaBootstrapConfiguration {

    @Value("${num.partitions}")
    private String defaultNumberOfPartitions;

    @Value("${kafka.bootstrap.servers.url}")
    private List<String> bootstrapServersAddresses;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${kafka.key.serializer}")
    private String keySerializerClassName;

    @Value("${kafka.value.serializer}")
    private String valueSerializerClassName;

    @Bean
    public KafkaProducer<String, String> kafkaLogProducer() {
        Properties properties = new Properties();

        //TODO: find out how to get pod id at runtime
        properties.setProperty("client.id", applicationName);
        properties.setProperty("bootstrap.servers", buildBootstrapServersString());
        properties.setProperty("key.serializer", keySerializerClassName);
        properties.setProperty("value.serializer", valueSerializerClassName);
        properties.setProperty("num.partitions", defaultNumberOfPartitions);
        //TODO: set log retention period
        return new KafkaProducer<>(properties);
    }

    private String buildBootstrapServersString() {
        final String resultString = this.bootstrapServersAddresses.stream().map(s -> s + ",").collect(Collectors.joining());
        return resultString.substring(0, resultString.length() - 1);
    }
}