package cn.lliiooll.mt.service;

import cn.lliiooll.mt.pojo.UserRole;

public interface UserService {


    UserRole findUserByUid(String uid);

    UserRole findUserByName(String name);

    UserRole findUserByQQ(String qq);

    UserRole findUserByEmail(String email);

   UserRole findUserByPhone(String phone);

    void createUser(UserRole role);

    void updateUser(UserRole role);
}
