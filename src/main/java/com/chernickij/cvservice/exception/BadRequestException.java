package com.chernickij.cvservice.exception;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(String msg, Throwable cause){
        super(msg, cause);
    }
}
