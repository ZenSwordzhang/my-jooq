package com.zsx.controller;

import com.zsx.entity.User;
import com.zsx.exception.NonExistingUserException;
import com.zsx.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exception")
public class ExceptionTestController {

    @Autowired
    private ExceptionService exceptionService;

    @PostMapping(value = "/test")
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

    @GetMapping(value = "/user")
    public String test(@RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("User not null");
        }
        if ("".equals(user.getName())) {
            throw new NonExistingUserException("User does not exist");
        }
        if ("run_ex".equals(user.getName())) {
            exceptionService.generateRuntimeException();
        }
        if ("non_exist".equals(user.getName())) {
            exceptionService.generateNonExistingUserException();
        }
        throw new RuntimeException("Test RuntimeException");
    }
}
