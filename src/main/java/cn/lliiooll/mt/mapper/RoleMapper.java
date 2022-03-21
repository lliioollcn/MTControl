package cn.lliiooll.mt.mapper;

import cn.lliiooll.mt.pojo.Role;
import cn.lliiooll.mt.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    Role findRoleByRoleId(int roleid);

    Role findRoleByRole(String role);

    void createRole(Role role);
}
