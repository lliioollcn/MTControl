package cn.lliiooll.mt.service.impl;

import cn.lliiooll.mt.mapper.UserMapper;
import cn.lliiooll.mt.pojo.UserRole;
import cn.lliiooll.mt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public UserRole findUserByUid(String uid) {
        return mapper.findUserByUid(uid);
    }

    @Override
    public UserRole findUserByName(String name) {
        return mapper.findUserByName(name);
    }

    @Override
    public UserRole findUserByQQ(String qq) {
        return mapper.findUserByQQ(qq);
    }

    @Override
    public void createUser(UserRole role) {
        mapper.createUser(role);
    }
}
