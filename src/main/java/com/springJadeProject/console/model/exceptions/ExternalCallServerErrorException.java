package com.springJadeProject.console.model.exceptions;

public class ExternalCallServerErrorException extends RuntimeException {
    public ExternalCallServerErrorException() {
        super();
    }

    public ExternalCallServerErrorException(String message) {
        super(message);
    }
}
