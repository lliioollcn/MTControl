package cn.lliiooll.mt.utils

import cn.hutool.crypto.SecureUtil
import cn.lliiooll.mt.beans.MTResponse
import cn.lliiooll.mt.pojo.UserRole
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import java.util.Random

object Utils {
    fun random(count: Int): String? {

        return StringBuilder().also {
            val zd = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")
            val random = Random()
            for (index in 0..count) {
                it.append(zd[random.nextInt(zd.size)])
            }
        }.toString()
    }

    @JvmStatic
    fun createUserData(userName: String, passWord: String, qq: String): UserRole {
        return UserRole().also {
            it.role = "1"
            it.password = passWord
            it.qq = qq
            it.username = userName
            it.uid = SecureUtil.sha1(random(10)).substring(0..10)
            it.banned = 0
            it.perms = "user:base"
        }
    }

}

fun HttpServletResponse.json(msg: String, data: Any, code: Int) {
    contentType = "application/json;charset=utf-8";
    write(this, MTResponse().also {
        it.msg = msg
        it.data = data
        it.code = code
    })
}


fun HttpServletResponse.json(msg: String, code: Int) {
    contentType = "application/json;charset=utf-8";
    write(this, MTResponse().also {
        it.msg = msg
        it.code = code
    })
}

fun HttpServletResponse.json(msg: String, data: Any) {
    contentType = "application/json;charset=utf-8";
    write(this, MTResponse().also {
        it.msg = msg
        it.data = data
        it.code = MTResponseCodes.SUCCESS
    })
}


fun HttpServletResponse.json(msg: String) {
    contentType = "application/json;charset=utf-8";
    write(this, MTResponse().also {
        it.msg = msg
        it.code = MTResponseCodes.SUCCESS
    })
}

fun write(resp: HttpServletResponse, data: MTResponse) {
    val writer = resp.writer
    writer.write(ObjectMapper().writeValueAsString(data))
    writer.flush()
    writer.close()
}