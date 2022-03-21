package cn.lliiooll.mt

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("cn.lliiooll.mt.mapper")
@SpringBootApplication
class MtControlApplication

fun main(args: Array<String>) {
    runApplication<MtControlApplication>(*args)
}
