package com.parsimony.toolmint_back.exception.custom;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
