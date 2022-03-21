package cn.lliiooll.mt.controller;

import cn.lliiooll.mt.beans.MTResponse;
import cn.lliiooll.mt.pojo.UserRole;
import cn.lliiooll.mt.service.RoleService;
import cn.lliiooll.mt.service.UserService;
import cn.lliiooll.mt.utils.RespUtils;
import cn.lliiooll.mt.utils.Utils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;


    @RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public MTResponse register(String userName, String passWord, String qq) {
        if (Objects.isNull(userName) || Objects.isNull(passWord) || Objects.isNull(qq)) {
            return RespUtils.error("用户名、密码和QQ都不能为空");
        }
        UserRole role = userService.findUserByName(userName);
        if (!Objects.isNull(role)) {
            return RespUtils.error("用户名已经被使用");
        }
        role = userService.findUserByQQ(qq);
        if (!Objects.isNull(role)) {
            return RespUtils.error("QQ已经被使用");
        }
        role = Utils.createUserData(userName, passEncoder.encode(passWord.trim()), qq);
        userService.createUser(role);
        //role.setPassword("");
        //role.setLastip("");
        return RespUtils.success("注册成功！", role);
    }

}
