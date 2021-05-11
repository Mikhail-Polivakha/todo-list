package com.example.todolistapp.service;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceLogProcessingDelegator {

    @Autowired
    private KafkaTemplate<LoggingLevel, ServiceLog> kafkaTemplate;

    @Value("${}")
    private String emergencyTopicName;

    @Value("${}")
    private String auditTopicName;

    /**
     * Send passed {@link ServiceLog} object to kafka topic
     * @param serviceLogToSend - serviceLog to send
     */
    public void send(ServiceLog serviceLogToSend) {

    }
}