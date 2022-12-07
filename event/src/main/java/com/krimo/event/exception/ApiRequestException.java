package com.karan.event.exception;

public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}
