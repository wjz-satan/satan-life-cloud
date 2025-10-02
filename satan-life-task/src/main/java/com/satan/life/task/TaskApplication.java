package com.satan.life.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 任务服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.satan.life.task.mapper")
@ComponentScan({"com.satan.life.task", "com.satan.life.common"})
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}