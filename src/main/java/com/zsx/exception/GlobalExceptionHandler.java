package com.zsx.exception;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "spring.errors.controller-advice", havingValue = "true")
public class GlobalExceptionHandler {


    @ExceptionHandler(NonExistingUserException.class)
    public ResponseEntity<Object> nonExistingUserException1(NonExistingUserException ex, HttpServletRequest request) {
        log.info("=========GlobalExceptionHandler.nonExistingUserException()=========");
        CustomizeHttpServletRequestWrapper wrapper = new CustomizeHttpServletRequestWrapper(request);
        BufferedReader reader = wrapper.getReader();
        String readStr = reader.lines().collect(Collectors.joining());
        log.info(readStr);
        log.error(String.format("GlobalExceptionHandler.nonExistingUserException(): %s", ex.getMessage()));
        return ResponseEntity.of(Optional.of(ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MethodArgumentTypeMismatchException> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("=========GlobalExceptionHandler.methodArgumentTypeMismatchException()=========");
        log.error(String.format("GlobalExceptionHandler.methodArgumentTypeMismatchException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<MaxUploadSizeExceededException> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.info("=========GlobalExceptionHandler.maxUploadSizeExceededException()=========");
        log.error(String.format("GlobalExceptionHandler.maxUploadSizeExceededException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpRequestMethodNotSupportedException> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("=========GlobalExceptionHandler.httpRequestMethodNotSupportedException()=========");
        log.error(String.format("GlobalExceptionHandler.httpRequestMethodNotSupportedException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<MissingRequestHeaderException> missingRequestHeaderException(MissingRequestHeaderException e) {
        log.info("=========GlobalExceptionHandler.missingRequestHeaderException()=========");
        log.error(String.format("GlobalExceptionHandler.missingRequestHeaderException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> unknownException(Exception e) {
        log.info("=========GlobalExceptionHandler.unknownException()=========");
        log.error(String.format("GlobalExceptionHandler.unknownException(): %s", e.getMessage()));
        return ResponseEntity.of(Optional.of(e));
    }

}
