package com.parsimony.toolmint_back.exception.custom;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
