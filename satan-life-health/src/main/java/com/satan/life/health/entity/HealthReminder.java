package com.satan.life.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 健康提醒实体类
 */
@Data
@TableName("health_reminder")
public class HealthReminder {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 提醒类型：1-喝水，2-运动，3-吃药，4-测量
     */
    private Integer reminderType;

    /**
     * 提醒标题
     */
    private String title;

    /**
     * 提醒内容
     */
    private String content;

    /**
     * 提醒时间
     */
    private LocalTime remindTime;

    /**
     * 重复类型：0-不重复，1-每天，2-每周，3-每月
     */
    private Integer repeatType;

    /**
     * 重复的星期几，逗号分隔
     */
    private String weekDays;

    /**
     * 重复的日期，逗号分隔
     */
    private String monthDays;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 逻辑删除标记：0-正常，1-删除
     */
    private Integer deleted;
}