package com.pacsport.service.exceptions;

public class InvalidAccessRequestException extends Exception {
    public InvalidAccessRequestException(String reason) {
        super(reason);
    }
}
