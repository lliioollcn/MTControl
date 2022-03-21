package cn.lliiooll.mt.controller;

import cn.lliiooll.mt.config.MTWebSecurityConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private HttpSecurity http;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Admin";
    }
}
