package com.example.todolistapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * An entity that represents log message from some service.
 *
 * @author Mikhail Polivakha
 * @since 03.05.2021
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceLog {

    /**
     * Actual log message
     */
    private String message;

    /**
     * service name that send this log
     */
    private String sourceServiceName;

    /**
     * importance (or level) of log designated by {@link #sourceServiceName}
     */
    private LoggingLevel loggingLevel;

    /**
     * This filed represents actual timestamp of event occurrence
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfOccurrence;

    /**
     * Most likely source microservice wont been run as single instance - it will be a couple of nodes (instances) of it
     * This field represents id of the instance, that sends this log
     */
    //TODO: find out how to extract node info in runtime
    private String nodeId;
}