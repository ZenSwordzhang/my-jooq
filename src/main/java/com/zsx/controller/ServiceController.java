package com.zsx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/service")
@RestController
public class ServiceController {

    @GetMapping("/status")
    public ResponseEntity<String> serviceCheck() {
        return ResponseEntity.ok("success");
    }
}
