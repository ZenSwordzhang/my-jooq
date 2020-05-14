package com.zsx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
@Slf4j
public class LogController {

    @GetMapping("/log/{msg}")
    public String getLog(@PathVariable("msg") String msg) {
        String str = String.format("%s--%s",msg, UUID.randomUUID().toString());
        log.info(str);
        return str;
    }
}
