package cn.lliiooll.mt.service;

import cn.lliiooll.mt.pojo.UserRole;

public interface UserService {


    UserRole findUserByUid(String uid);

    UserRole findUserByName(String name);

    UserRole findUserByQQ(String qq);

    void createUser(UserRole role);
}
