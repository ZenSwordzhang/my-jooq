package com.zsx.controller;

import com.zsx.entity.User;
import com.zsx.exception.NonExistingUserException;
import com.zsx.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/exception")
public class ExceptionTestController {

    @Autowired
    private ExceptionService exceptionService;

    @GetMapping(value = "/test")
    public String test(String name) {
        if (name == null) {
            throw new RuntimeException("Name not null");
        }
        if ("".equals(name)) {
            throw new NonExistingUserException("User does not exist");
        }
        if ("run_ex".equals(name)) {
            exceptionService.generateRuntimeException();
        }
        if ("non_exist".equals(name)) {
            exceptionService.generateNonExistingUserException();
        }
        throw new RuntimeException("Test RuntimeException");
    }

    @PostMapping(value = "/user")
    public String test(@RequestBody User user) {
        throw new RuntimeException("Test RuntimeException");
    }

    @PostMapping(value = "/file")
    public String test(@RequestParam("file") MultipartFile file) {
        throw new RuntimeException("File reading exception");
    }
}
