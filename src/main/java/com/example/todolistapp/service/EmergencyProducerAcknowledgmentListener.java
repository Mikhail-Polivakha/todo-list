package com.example.todolistapp.service;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmergencyProducerAcknowledgmentListener implements ProducerListener<LoggingLevel, ServiceLog> {

    @Override
    public void onSuccess(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata) {
        log.info("Success: ServiceLog {}, Partition : {}, Offset : {}", producerRecord.value(), recordMetadata.partition(), recordMetadata.offset());
    }

    @Override
    public void onError(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata, Exception exception) {

    }
}