package com.zsx.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Optional;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<Exception> unknownException(Exception e) {
        log.error(String.format("GlobalExceptionHandler.unknownException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler
    public ResponseEntity<NonExistingUserException> nonExistingUserException(NonExistingUserException e) {
        log.error(String.format("GlobalExceptionHandler.nonExistingUserException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler
    public ResponseEntity<MethodArgumentTypeMismatchException> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(String.format("GlobalExceptionHandler.methodArgumentTypeMismatchException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler
    public ResponseEntity<MaxUploadSizeExceededException> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(String.format("GlobalExceptionHandler.maxUploadSizeExceededException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler
    public ResponseEntity<HttpRequestMethodNotSupportedException> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(String.format("GlobalExceptionHandler.httpRequestMethodNotSupportedException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler
    public ResponseEntity<MissingRequestHeaderException> missingRequestHeaderException(MissingRequestHeaderException e) {
        log.error(String.format("GlobalExceptionHandler.missingRequestHeaderException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

}
