package com.satan.life.note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 笔记服务启动类
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableDiscoveryClient
@MapperScan("com.satan.life.note.mapper")
@ComponentScan({
        "com.satan.life.note",
        "com.satan.life.common"
})
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }
}