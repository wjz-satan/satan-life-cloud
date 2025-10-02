package com.satan.life.common.entity.health;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

import java.time.LocalTime;

/**
 * 健康提醒实体类
 */
@TableName("health_reminder")
public class HealthReminder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 提醒类型（如：喝水、运动、吃药等）
     */
    private String reminderType;

    /**
     * 提醒时间
     */
    private LocalTime reminderTime;

    /**
     * 重复频率（如：每天、每周、每月）
     */
    private String repeatFrequency;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 提醒内容
     */
    private String content;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getRepeatFrequency() {
        return repeatFrequency;
    }

    public void setRepeatFrequency(String repeatFrequency) {
        this.repeatFrequency = repeatFrequency;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}