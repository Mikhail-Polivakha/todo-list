package com.example.todolistapp.configuration;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class KafkaBootstrapConfiguration {

    @Value("${kafka.bootstrap.servers.url}")
    private List<String> bootstrapServersAddresses;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${kafka.key.serializer}")
    private String keySerializerClassName;

    @Value("${kafka.value.serializer}")
    private String valueSerializerClassName;

    private String buildBootstrapServersString() {
        final String resultString = this.bootstrapServersAddresses.stream().map(s -> s + ",").collect(Collectors.joining());
        return resultString.substring(0, resultString.length() - 1);
    }

    @Bean
    public Map<String, Object> producerConfigurationSettings() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, applicationName);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, buildBootstrapServersString());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClassName);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClassName);
        /*
         * TODO: replace properties below with configuration on a server to make log store for infinite time
         *  properties.setProperty("log.retention.hours", "-1");
         *  properties.setProperty("log.retention.bytes", "-1");
         */
        return properties;
    }

    @Bean
    public ProducerFactory<LoggingLevel, ServiceLog> loggingTopicProducerFactory(Map<String, Object> producerConfigurationSettings) {
        return new DefaultKafkaProducerFactory<>(producerConfigurationSettings);
    }

    @Bean
    public KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate(ProducerFactory<LoggingLevel, ServiceLog> producerFactory) {
        KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        return kafkaTemplate;
    }

}