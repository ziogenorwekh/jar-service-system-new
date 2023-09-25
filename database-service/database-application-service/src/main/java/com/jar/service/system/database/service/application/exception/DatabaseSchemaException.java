package com.jar.service.system.database.service.application.exception;


public class DatabaseSchemaException extends RuntimeException {

    public DatabaseSchemaException(String msg) {
        super(msg);
    }

    public DatabaseSchemaException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
