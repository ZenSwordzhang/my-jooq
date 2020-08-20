package com.zsx.exception;

import com.zsx.http.CustomizeHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler1 extends ResponseEntityExceptionHandler {

    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        log.info("=========GeneralExceptionHandler1.handleExceptionInternal()=========");
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(NonExistingUserException.class)
    public ResponseEntity<Object> nonExistingUserException1(NonExistingUserException ex, HttpServletRequest request) {
        log.info("=========GeneralExceptionHandler1.nonExistingUserException()=========");
        CustomizeHttpServletRequestWrapper wrapper = new CustomizeHttpServletRequestWrapper(request);
        BufferedReader reader = wrapper.getReader();
        String readStr = reader.lines().collect(Collectors.joining());
        log.info(readStr);
        log.error(String.format("GeneralExceptionHandler1.nonExistingUserException(): %s", ex.getMessage()));
        return handleExceptionInternal(ex, readStr, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, new ServletWebRequest(request));
    }
}
