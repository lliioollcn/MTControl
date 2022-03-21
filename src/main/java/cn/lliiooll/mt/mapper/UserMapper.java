package cn.lliiooll.mt.mapper;

import cn.lliiooll.mt.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    UserRole findUserByUid(String uid);

    UserRole findUserByName(String name);

    UserRole findUserByQQ(String qq);

    void createUser(UserRole role);
}
