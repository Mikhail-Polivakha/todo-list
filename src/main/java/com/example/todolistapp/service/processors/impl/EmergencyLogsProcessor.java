package com.example.todolistapp.service.processors.impl;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import com.example.todolistapp.service.processors.LogProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EmergencyLogsProcessor implements LogProcessor {

    @Autowired @Qualifier("emergencyKafkaTemplate") private KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate;

    @Value("${kafka.topic.emergency.name}")
    private String emergencyKafkaTopicName;

    @Override
    public Set<LoggingLevel> getLoggingLevels() {
        return Set.of(LoggingLevel.WARNING, LoggingLevel.ERROR);
    }

    @Override
    public void handleMessage(ServiceLog serviceLog) {
        ProducerRecord<LoggingLevel, ServiceLog> emergencyLogProducerRecord = new ProducerRecord<>(
                emergencyKafkaTopicName,
                null,
                System.currentTimeMillis(),
                serviceLog.getLoggingLevel(),
                serviceLog,
                null
        );
        kafkaTemplate.send(emergencyLogProducerRecord);
    }

}
