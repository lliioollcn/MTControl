package cn.lliiooll.mt.config

import cn.hutool.json.JSONUtil
import cn.lliiooll.mt.pojo.UserRole
import cn.lliiooll.mt.service.RoleService
import cn.lliiooll.mt.service.UserService
import cn.lliiooll.mt.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class MTUserDetailsService : UserDetailsService {


    private val passEncoder = BCryptPasswordEncoder()

    @Autowired
    private val service: UserService? = null

    @Autowired
    private val roleService: RoleService? = null

    @Bean
    private fun pwEncoder(): BCryptPasswordEncoder? {
        return passEncoder
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val role: UserRole? = service?.findUserByName(username)
        return User.builder().also {
            it.username(username)
            if (role != null) {
                it.password(role.password)
                it.disabled(role.banned == 2)
                it.accountLocked(role.banned == 1)
                val roleIds = role.role.split(",")
                val roles: List<GrantedAuthority> = buildList {
                    for (roleIdStr in roleIds) {
                        val roleId = Integer.parseInt(roleIdStr)
                        val roleStr = roleService?.findRoleByRoleId(roleId)
                        if (!Objects.isNull(roleStr)) {
                            roleStr?.role?.let { it1 -> add(SimpleGrantedAuthority("ROLE_$it1")) }
                        }
                    }
                    for (perm in role.perms.split(",")) {
                        add(SimpleGrantedAuthority(perm))
                    }
                }
                it.authorities(roles)
            } else {
                it.roles("NONE")
                it.password(pwEncoder()?.encode(Utils.random(10)))
            }
        }.build()
    }
}