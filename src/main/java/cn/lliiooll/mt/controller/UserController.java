package cn.lliiooll.mt.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpSecurity http;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, User";
    }

    @GetMapping("/hello1")
    public String hello1() {
        return "Hello, User";
    }
}
