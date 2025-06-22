package com.parsimony.toolmint_back.exception.custom;

import java.util.Map;
import java.util.StringJoiner;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String entityName, Map<String, Object> parameters) {
        super(buildMessage(entityName, parameters));
    }

    public DataNotFoundException(String entityName, String parameterName, Object parameterValue) {
        this(entityName, Map.of(parameterName, parameterValue));
    }

    private static String buildMessage(String entityName, Map<String, Object> parameters) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            joiner.add(entry.getKey() + " = " + entry.getValue());
        }
        return entityName + " not found with (" + joiner + ")";
    }
}