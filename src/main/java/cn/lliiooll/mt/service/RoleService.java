package cn.lliiooll.mt.service;

import cn.lliiooll.mt.pojo.Role;

public interface RoleService {


    Role findRoleByRoleId(int roleid);

    Role findRoleByRole(String role);

    void createRole(Role role);
}
