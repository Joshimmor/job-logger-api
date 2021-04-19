package com.fairview.joblogger.env;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JtAuthException extends RuntimeException {

    public JtAuthException(String message) {
        super(message);
    }
}