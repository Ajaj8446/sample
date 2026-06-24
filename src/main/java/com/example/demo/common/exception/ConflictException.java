package com.example.demo.common.exception;

public class ConflictException extends RuntimeException {

    private final String resourceName;

    public ConflictException(String message) {
        super(message);
        this.resourceName = null;
    }

    public ConflictException(String resourceName, String message) {
        super(message);
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
