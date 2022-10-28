package com.example.twiter.exceptionHandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class RestApiException {
    private String errorMessage;
    private org.springframework.http.HttpStatus HttpStatus;
}