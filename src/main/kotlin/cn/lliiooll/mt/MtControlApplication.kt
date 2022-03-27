package cn.lliiooll.mt

import cn.lliiooll.mt.utils.YggdrasilUtils
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("cn.lliiooll.mt.mapper")
@SpringBootApplication
class MtControlApplication

fun main(args: Array<String>) {
    YggdrasilUtils.init()
    runApplication<MtControlApplication>(*args)
}
