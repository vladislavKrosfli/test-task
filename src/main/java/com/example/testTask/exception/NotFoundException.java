package com.example.testTask.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
