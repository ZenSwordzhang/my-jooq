package com.zsx.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "spring.errors.controller-advice", havingValue = "true")
public class GlobalExceptionHandler {


    @ExceptionHandler(NonExistingUserException.class)
    public ResponseEntity<Object> nonExistingUserException1(NonExistingUserException ex, HttpServletRequest request) {
        log.info("=========GlobalExceptionHandler.nonExistingUserException()=========");
        return ResponseEntity.of(Optional.of(ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MethodArgumentTypeMismatchException> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("=========GlobalExceptionHandler.methodArgumentTypeMismatchException()=========");
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<MaxUploadSizeExceededException> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.info("=========GlobalExceptionHandler.maxUploadSizeExceededException()=========");
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpRequestMethodNotSupportedException> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("=========GlobalExceptionHandler.httpRequestMethodNotSupportedException()=========");
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<MissingRequestHeaderException> missingRequestHeaderException(MissingRequestHeaderException e) {
        log.info("=========GlobalExceptionHandler.missingRequestHeaderException()=========");
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> unknownException(Exception e) {
        log.info("=========GlobalExceptionHandler.unknownException()=========");
        return ResponseEntity.of(Optional.of(e));
    }

}
