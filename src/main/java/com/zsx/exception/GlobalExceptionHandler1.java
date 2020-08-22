//package com.zsx.exception;
//
//import com.zsx.http.CustomizeHttpServletRequestWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//
//@Slf4j
//@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class GlobalExceptionHandler1 extends ResponseEntityExceptionHandler {
//
//    @Override
//    @SuppressWarnings("NullableProblems")
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
//                                                             HttpHeaders headers,
//                                                             HttpStatus status,
//                                                             WebRequest request) {
//        try {
//            log.info("=========GeneralExceptionHandler1.handleExceptionInternal()=========");
//            HttpServletRequest req = ((ServletWebRequest) request).getRequest();
//            String bodyParams = req.getReader().lines().collect(Collectors.joining());
//            log.info("RequestBody Parameters : {} ", bodyParams);
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.error("GeneralExceptionHandler1.handleExceptionInternal().IOException : {}", ex.getMessage());
//        }
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> exception(Exception ex, HttpServletRequest request) {
//        log.info("=========GeneralExceptionHandler1.exception()=========");
//        CustomizeHttpServletRequestWrapper wrapper = new CustomizeHttpServletRequestWrapper(request);
//        BufferedReader reader = wrapper.getReader();
//        String bodyParams = reader.lines().collect(Collectors.joining());
//        log.info("RequestBody Parameters : {} ", bodyParams);
//        log.error("GeneralExceptionHandler1.exception() : {}", ex.getMessage());
//        return handleExceptionInternal(ex, bodyParams, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, new ServletWebRequest(request));
//    }
//}
