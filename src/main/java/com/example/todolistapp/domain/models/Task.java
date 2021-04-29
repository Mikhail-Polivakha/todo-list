package com.example.todolistapp.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @MongoId(FieldType.STRING)
    private String id;

    @Field(targetType = FieldType.STRING)
    private String description;

    @Field(targetType = FieldType.TIMESTAMP)
    private LocalDateTime deadline;

    @Field(targetType = FieldType.TIMESTAMP)
    private LocalDateTime dateOfCreation;

    @DBRef(lazy = true)
    private Set<Task> subTasks;
    
    @DBRef(lazy = true)
    private Set<Task> dependencyTasks;

    @Field(targetType = FieldType.STRING)
    private TaskType taskType;

    @Field(targetType = FieldType.STRING)
    private EmergencyType emergencyType;
}