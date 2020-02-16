package com.pacsport.service.exceptions;

public class ExistedEmailException extends Exception {
    private static final String messageFormat = "email %s was used";

    public ExistedEmailException(String email) {
        super(String.format(messageFormat, email));
    }
}
