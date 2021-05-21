package com.lww.server;

//启动类

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.lww.server.mapper")               //mapper接口扫描
//关闭security自动的安全验证机器 以免每次进入页面都要登录...
public class LebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LebApplication.class,args);
    }
}
