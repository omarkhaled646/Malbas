package com.aden.malbas.exception;

public class SizeNotAvailableException extends RuntimeException{

    public SizeNotAvailableException(String message) {
        super(message);
    }

    public SizeNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeNotAvailableException(Throwable cause) {
        super(cause);
    }
}
