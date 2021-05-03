package com.example.todolistapp.resources;

import com.example.todolistapp.domain.ServiceLog;
import com.example.todolistapp.service.ServiceLogKafkaAppenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-log")
public class ServiceLogResource {

    private final ServiceLogKafkaAppenderService serviceLogKafkaAppenderService;

    //TODO: Attach swagger here to describe REST API Endpoint
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@RequestBody ServiceLog serviceLog) {
        serviceLogKafkaAppenderService.send(serviceLog);
    }
}