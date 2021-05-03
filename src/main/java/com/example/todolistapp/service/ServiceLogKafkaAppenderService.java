package com.example.todolistapp.service;

import com.example.todolistapp.domain.ServiceLog;
import org.springframework.stereotype.Service;

@Service
public class ServiceLogKafkaAppenderService {

    /**
     * Send passed {@link ServiceLog} object to kafka topic
     * @param serviceLogToSend - serviceLog to send
     */
    public void send(ServiceLog serviceLogToSend) {

    }
}