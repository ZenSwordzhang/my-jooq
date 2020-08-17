package com.zsx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @PostMapping(value = "/test")
    public String test() {
        throw new RuntimeException("Test RuntimeException");
    }
}
