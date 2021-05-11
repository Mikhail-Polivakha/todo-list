package com.example.todolistapp.service;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyProducerAcknowledgmentListener implements ProducerListener<LoggingLevel, ServiceLog> {

    private final ServiceLogManager serviceLogManager;

    @Override
    public void onSuccess(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata) {
        logSuccess(producerRecord, recordMetadata);
    }

    @Override
    public void onError(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        logFailure(producerRecord, exception);
        serviceLogManager.saveUnsuccessfulServiceLog(producerRecord.value(), exception.getMessage());
    }

    private void logSuccess(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata) {
        log.info("Emergency Log delivery success: ServiceLog {}, Partition : {}, Offset : {}",
                 producerRecord.value(),
                 recordMetadata.partition(),
                 recordMetadata.offset());
    }

    private void logFailure(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, Exception exception) {
        log.warn("Emergency Log delivery failure: ServiceLog {}, Partition : {}, Key {}, Timestamp : {}, Exception : {}, Exception message : {}",
                 producerRecord.value(),
                 producerRecord.partition(),
                 producerRecord.key(),
                 producerRecord.timestamp(),
                 exception.getCause(),
                 exception.getMessage()
        );
    }
}