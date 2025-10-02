package com.satan.life.analytics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 数据统计与分析服务主启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.satan.life.analytics.mapper")
public class AnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

}