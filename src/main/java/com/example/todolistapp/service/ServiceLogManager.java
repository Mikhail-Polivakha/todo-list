package com.example.todolistapp.service;

import com.example.todolistapp.domain.ServiceLog;
import com.example.todolistapp.domain.UnsuccessfulServiceLog;
import com.example.todolistapp.domain.fields.UnsuccessfulServiceLogField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceLogManager {

    private final MongoTemplate mongoTemplate;

    public void saveUnsuccessfulServiceLog(ServiceLog serviceLog, String exceptionName) {
        log.info("Persist serviceLog object : {} as UnsuccessfulServiceLog", serviceLog);
        UnsuccessfulServiceLog unsuccessfulServiceLog = UnsuccessfulServiceLog.of(serviceLog, exceptionName);
        mongoTemplate.save(unsuccessfulServiceLog);
    }

    public UnsuccessfulServiceLog deleteUnsuccessfulServiceLog(ObjectId serviceLogId) {
        mongoTemplate.remove(Query.query(Criteria.where(UnsuccessfulServiceLogField.ID).is(serviceLogId)), UnsuccessfulServiceLog.class);
    }
}
