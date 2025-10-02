package com.satan.life.health;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 健康管理服务启动类
 */
@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
@EnableDiscoveryClient
@MapperScan("com.satan.life.health.mapper")
@ComponentScan("com.satan.life")
public class HealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }
}