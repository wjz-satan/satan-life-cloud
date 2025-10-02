package com.satan.life.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 基础配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "satan.life")
public class BaseProperties {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 环境
     */
    private String env;

    /**
     * 是否开启调试模式
     */
    private boolean debug;

    /**
     * 服务访问路径前缀
     */
    private String contextPath;
}