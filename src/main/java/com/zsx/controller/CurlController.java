package com.zsx.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/curl")
@RestController
public class CurlController {

    @GetMapping("/msg")
    public String getMsgContent(String id) {
        return id + ", get success";
    }

    @GetMapping("/msgs")
    public String getMsgs(@RequestHeader("token") String token) {
        return token + ", get success";
    }

    @GetMapping("/msg1")
    public String getMsg1(@CookieValue(value = "username") String username) {
        return username + ", get success";
    }

    @GetMapping("/msg/{name}")
    public String getMsg(@PathVariable("name") String name) {
        return "hello world, " + name;
    }

    @PostMapping("/msg")
    public String updateMsg(String id) {
        return id + ", update success";
    }

    @DeleteMapping("/msg")
    public String deleteMsg(String id) {
        return id + ", delete success";
    }

    @PutMapping("/msg")
    public String putMsg(String id) {
        return id + ", put success";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        return uploadFile.getOriginalFilename() + ", upload success";
    }


}
