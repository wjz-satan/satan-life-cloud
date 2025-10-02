package com.satan.life.finance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 财务服务启动类
 */
@SpringBootApplication
@MapperScan("com.satan.life.finance.mapper")
@ComponentScan({
        "com.satan.life.finance",
        "com.satan.life.common"
})
public class FinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }
}