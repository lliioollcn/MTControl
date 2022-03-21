package cn.lliiooll.mt.auth

import cn.lliiooll.mt.utils.MTResponseCodes
import cn.lliiooll.mt.utils.json
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

object AuthHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?
    ) {
        if (exception?.message == "Cannot pass null or empty values to constructor") response?.json(
            "用户不存在", MTResponseCodes.FAILED
        ) else if (exception?.message == "Bad credentials") response?.json(
            "账户或密码错误", MTResponseCodes.FAILED
        ) else if (exception != null) {
            exception.message?.let { response?.json(it, MTResponseCodes.FAILED) }
        }
    }
}