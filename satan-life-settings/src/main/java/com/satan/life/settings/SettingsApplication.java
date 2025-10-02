package com.satan.life.settings;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统设置服务启动类
 */
@SpringBootApplication
@MapperScan("com.satan.life.settings.mapper")
@ComponentScan("com.satan.life")
public class SettingsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SettingsApplication.class, args);
    }
}