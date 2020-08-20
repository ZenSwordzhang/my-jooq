package com.zsx.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({CustomizeErrorController.ERROR_PATH})
@ConditionalOnProperty(name = "spring.errors.controller", havingValue = "true")
public class CustomizeErrorController extends AbstractErrorController {

    static final String ERROR_PATH = "/error";

    public CustomizeErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
        log.info("=========CustomizeErrorController.CustomizeErrorController()=========");
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        log.info("=========CustomizeErrorController.error()=========");
        Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.of());
        HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>(body, status);
    }

    @Override
    public String getErrorPath() {
        log.info("=========CustomizeErrorController.getErrorPath()=========");
        return ERROR_PATH;
    }
}
