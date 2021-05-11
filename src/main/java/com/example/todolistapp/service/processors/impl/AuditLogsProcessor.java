package com.example.todolistapp.service.processors.impl;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import com.example.todolistapp.service.processors.LogProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuditLogsProcessor implements LogProcessor {

    @Autowired @Qualifier("auditKafkaTemplate") private KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate;

    @Value("${kafka.topic.audit.name}")
    private String auditTopicName;

    @Override
    public Set<LoggingLevel> getLoggingLevels() {
        return Set.of(LoggingLevel.DEBUG, LoggingLevel.INFO);
    }

    @Override
    public void handleMessage(ServiceLog serviceLog) {
        ProducerRecord<LoggingLevel, ServiceLog> auditLogProducerRecord = new ProducerRecord<>(
                auditTopicName,
                null,
                System.currentTimeMillis(),
                serviceLog.getLoggingLevel(),
                serviceLog,
                null
        );
        kafkaTemplate.send(auditLogProducerRecord);
    }
}
