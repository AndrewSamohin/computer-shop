package com.example.computershop.exception;

public class InvalidProductTypeException extends RuntimeException {
    public InvalidProductTypeException(String type) {
        super("Unsupported product type: " + type);
    }
}
