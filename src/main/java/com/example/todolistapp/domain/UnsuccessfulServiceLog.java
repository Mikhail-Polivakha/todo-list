package com.example.todolistapp.domain;

import com.example.todolistapp.domain.fields.UnsuccessfulServiceLogField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = UnsuccessfulServiceLogField.COLLECTION_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class UnsuccessfulServiceLog {

    @MongoId(FieldType.OBJECT_ID)
    @Field(UnsuccessfulServiceLogField.ID)
    private ObjectId id;

    @Field(value = UnsuccessfulServiceLogField.SERVICE_ID)
    private ServiceLog serviceLog;

    @Field(value = UnsuccessfulServiceLogField.LAST_ATTEMPT_DATE, targetType = FieldType.TIMESTAMP)
    private LocalDateTime lastAttemptDate;

    @Field(UnsuccessfulServiceLogField.ALREADY_PASSED_ATTEMPTS_COUNTER)
    private int alreadyPassedAttemptsCounter;

    @Field(UnsuccessfulServiceLogField.EXCEPTION_NAME)
    private String exceptionName;

    public static UnsuccessfulServiceLog of(ServiceLog serviceLog) {
        return new UnsuccessfulServiceLog(null, serviceLog, LocalDateTime.now(), 0, null);
    }

    public static UnsuccessfulServiceLog of(ServiceLog serviceLog, String exceptionName) {
        return new UnsuccessfulServiceLog(null, serviceLog, LocalDateTime.now(), 0, exceptionName);
    }
}