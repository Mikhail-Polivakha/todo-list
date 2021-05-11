package com.example.todolistapp.service;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import com.example.todolistapp.service.processors.LogProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceLogProcessingDelegator {

    private final Set<Entry<Set<LoggingLevel>, LogProcessor>> logProcessorsMap;

    public ServiceLogProcessingDelegator(List<LogProcessor> logProcessorsList) {
        this.logProcessorsMap = logProcessorsList.stream()
                                                 .collect(Collectors.toMap(LogProcessor::getLoggingLevels, logProcessor -> logProcessor))
                                                 .entrySet();
    }

    /**
     * Send passed {@link ServiceLog} object to kafka topic
     * @param serviceLogToSend - serviceLog to send
     */
    public void send(ServiceLog serviceLogToSend) {
        for (Entry<Set<LoggingLevel>, LogProcessor> entry : logProcessorsMap) {
            if (entry.getKey().contains(serviceLogToSend.getLoggingLevel())) {
                entry.getValue().handleMessage(serviceLogToSend);
            }
        }
    }
}