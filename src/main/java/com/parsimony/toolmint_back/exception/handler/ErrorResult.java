package com.parsimony.toolmint_back.exception.handler;

import java.time.LocalDateTime;

public record ErrorResult(
        int status,
        String message,
        String path,
        String timestamp
) {
    public ErrorResult(int status, String message, String path) {
        this(status, message, path, LocalDateTime.now().toString());
    }

    public ErrorResult(int status, String message) {
        this(status, message, null, LocalDateTime.now().toString());
    }
}