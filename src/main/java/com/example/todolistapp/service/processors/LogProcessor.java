package com.example.todolistapp.service.processors;

import com.example.todolistapp.domain.LoggingLevel;
import com.example.todolistapp.domain.ServiceLog;

import java.util.Set;

/**
 * Interface to be implemented by all log processors who are interested in
 * logs with level specified in {@link #getLoggingLevels()}
 *
 * @author Mikhail Polivakha
 */
public interface LogProcessor {

    /**
     * Set of Logging levels served by this Log processor
     * @return Set of Logging levels served by this Log processor
     */
    Set<LoggingLevel> getLoggingLevels();

    /**
     * Method fot message processing itself
     * @param serviceLog - serviceLog object to pe processed
     */
    void handleMessage(ServiceLog serviceLog);
}
