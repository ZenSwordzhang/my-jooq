package com.zsx.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NonExistingUserException extends RuntimeException {

    public NonExistingUserException() {
        super();
    }

    public NonExistingUserException(String message) {
        super(message);
        log.info("=========NonExistingUserException.NonExistingUserException()=========");
        log.error(String.format("%s: %s", NonExistingUserException.class.getName(), message));
    }

    public NonExistingUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistingUserException(Throwable cause) {
        super(cause);
    }

    protected NonExistingUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
