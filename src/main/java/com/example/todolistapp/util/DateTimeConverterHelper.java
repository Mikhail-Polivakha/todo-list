package com.example.todolistapp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class DateTimeConverterHelper {

    private final ZoneOffset applicationTimeZone;

    public DateTimeConverterHelper(@Value("${time.zone.default.id}") String timeZone) {
        this.applicationTimeZone = ZoneOffset.of(timeZone);
    }

    public long toEpochSeconds(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(applicationTimeZone);
    }
}
