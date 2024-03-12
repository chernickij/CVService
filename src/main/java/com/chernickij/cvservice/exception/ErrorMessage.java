package com.chernickij.cvservice.exception;

public record ErrorMessage(int statusCode, String message, String description) {

}
