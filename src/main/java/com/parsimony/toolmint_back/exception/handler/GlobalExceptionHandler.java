package com.parsimony.toolmint_back.exception.handler;

import com.parsimony.toolmint_back.exception.custom.ConflictException;
import com.parsimony.toolmint_back.exception.custom.DataNotFoundException;
import com.parsimony.toolmint_back.exception.custom.InvalidInputException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleInvalidInputException(InvalidInputException ex, HttpServletRequest request) {
        log.error("message: {}, position: {}", ex.getMessage(), getTopStackTrace(ex));
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request) {
        log.error("message: {}, position: {}", ex.getMessage(), getTopStackTrace(ex));
        return new ErrorResult(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResult handleConflictException(ConflictException ex, HttpServletRequest request) {
        log.error("message: {}, position: {}", ex.getMessage(), getTopStackTrace(ex));
        return new ErrorResult(HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
    }

    private String getTopStackTrace(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        return stackTrace.length > 0 ? stackTrace[0].toString() : "unknown";
    }
}