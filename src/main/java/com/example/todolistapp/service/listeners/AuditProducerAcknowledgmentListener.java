package com.example.todolistapp.service.listeners;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditProducerAcknowledgmentListener implements ProducerListener<LoggingLevel, ServiceLog> {

    @Override
    public void onSuccess(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata) {
        log.debug("Audit log publishment success: ServiceLog : {}, Partition: {}, Offset : {}",
                  producerRecord.value(),
                  recordMetadata.partition(),
                  recordMetadata.offset());
    }

    @Override
    public void onError(ProducerRecord<LoggingLevel, ServiceLog> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        log.warn("Audit log publishment failure: ServiceLog : {}, Partition: {}, Exception : {}, Exception message : {}",
                 producerRecord.value(),
                 recordMetadata != null ? recordMetadata.partition() : "unrecognized",
                 exception.getClass().getSimpleName(),
                 exception.getMessage()
        );
    }
}