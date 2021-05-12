package com.example.todolistapp.configuration;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class KafkaBootstrapConfiguration {

    @Value("${kafka.bootstrap.servers.url}")
    private List<String> bootstrapServersAddresses;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${kafka.key.serializer}")
    private String keySerializerClassName;

    @Value("${kafka.value.serializer}")
    private String valueSerializerClassName;

    @Value("${kafka.topic.emergency.name}")
    private String emergencyTopicName;

    @Value("${kafka.topic.audit.name}")
    private String auditTopicName;

    @Value("${kafka.topic.emergency.partitions.num}")
    private int emergencyPartitionsAmount;

    @Value("${kafka.topic.audit.partitions.num}")
    private int auditPartitionsAmount;

    @Value("${kafka.topic.emergency.replication-factor}")
    private int emergencyReplicationFactor;

    @Value("${kafka.topic.audit.replication-factor}")
    private int auditReplicationFactor;

    private String buildBootstrapServersString() {
        final String resultString = this.bootstrapServersAddresses.stream().map(s -> s + ",").collect(Collectors.joining());
        return resultString.substring(0, resultString.length() - 1);
    }

    private void attachGeneralProducerSettings(Map<String, Object> properties) {
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, applicationName);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, buildBootstrapServersString());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClassName);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClassName);
    }

    @Bean
    public Map<String, Object> auditProducerConfigurationSettings() {
        Map<String, Object> properties = new HashMap<>();
        attachGeneralProducerSettings(properties);
        properties.put(ProducerConfig.ACKS_CONFIG, 1);
        return properties;
    }

    @Bean
    public Map<String, Object> emergencyProducerConfigurationSettings() {
        Map<String, Object> properties = new HashMap<>();
        attachGeneralProducerSettings(properties);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

    @Bean("auditTopicProducerFactory")
    public ProducerFactory<LoggingLevel, ServiceLog> auditTopicProducerFactory(@Qualifier("auditProducerConfigurationSettings")
                                                                                           Map<String, Object> auditProducerConfigurationSettings) {
        log.info("audit producer properties: {}", auditProducerConfigurationSettings);
        return new DefaultKafkaProducerFactory<>(auditProducerConfigurationSettings);
    }

    @Bean("emergencyTopicProducerFactory")
    public ProducerFactory<LoggingLevel, ServiceLog> emergencyTopicProducerFactory(@Qualifier("emergencyProducerConfigurationSettings")
                                                                                               Map<String, Object> emergencyProducerConfigurationSettings) {
        log.info("emergency producer properties: {}", emergencyProducerConfigurationSettings);
        return new DefaultKafkaProducerFactory<>(emergencyProducerConfigurationSettings);
    }

    @Bean
    public KafkaTemplate<LoggingLevel, ServiceLog> auditKafkaTemplate(@Qualifier("auditTopicProducerFactory")
                                                                              ProducerFactory<LoggingLevel, ServiceLog> producerFactory) {
        KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        log.info("Audit kafka template config: {}", kafkaTemplate.getProducerFactory().getConfigurationProperties());
        return kafkaTemplate;
    }

    @Bean
    public KafkaTemplate<LoggingLevel, ServiceLog> emergencyKafkaTemplate(@Qualifier("emergencyTopicProducerFactory")
                                                                                      ProducerFactory<LoggingLevel, ServiceLog> producerFactory) {
        KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setMessageConverter(new StringJsonMessageConverter());
        log.info("Emergency kafka template config: {}", kafkaTemplate.getProducerFactory().getConfigurationProperties());
        return kafkaTemplate;
    }

    @Bean
    public NewTopic emergencyTopic() {
        return TopicBuilder.name(emergencyTopicName)
                           .partitions(emergencyPartitionsAmount)
                           .replicas(emergencyReplicationFactor)
                           .build();
    }

    @Bean
    public NewTopic auditTopic() {
        return TopicBuilder.name(auditTopicName)
                           .partitions(auditPartitionsAmount)
                           .replicas(auditReplicationFactor)
                           .build();
    }
}