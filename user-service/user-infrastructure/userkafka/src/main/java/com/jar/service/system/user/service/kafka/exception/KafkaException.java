package com.jar.service.system.user.service.kafka.exception;

public class KafkaException extends RuntimeException {

    public KafkaException(String message) {
        super(message);
    }

    public KafkaException(String message, Throwable cause) {
        super(message, cause);
    }
}
