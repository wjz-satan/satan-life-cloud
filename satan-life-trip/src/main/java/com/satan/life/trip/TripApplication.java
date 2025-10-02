package com.satan.life.trip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 行程安排服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.satan.life.trip.mapper")
@ComponentScan("com.satan.life")
public class TripApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripApplication.class, args);
    }
}