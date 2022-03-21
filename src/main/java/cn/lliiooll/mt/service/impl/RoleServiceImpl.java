package cn.lliiooll.mt.service.impl;

import cn.lliiooll.mt.mapper.RoleMapper;
import cn.lliiooll.mt.mapper.UserMapper;
import cn.lliiooll.mt.pojo.Role;
import cn.lliiooll.mt.pojo.UserRole;
import cn.lliiooll.mt.service.RoleService;
import cn.lliiooll.mt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper mapper;

    @Override
    public Role findRoleByRoleId(int roleid) {
        return mapper.findRoleByRoleId(roleid);
    }

    @Override
    public Role findRoleByRole(String role) {
        return mapper.findRoleByRole(role);
    }

    @Override
    public void createRole(Role role) {
        mapper.createRole(role);
    }
}
