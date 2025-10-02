package com.satan.life.common.entity.health;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 睡眠记录实体类
 */
@TableName("sleep_record")
public class SleepRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 入睡时间
     */
    private LocalDateTime sleepTime;

    /**
     * 起床时间
     */
    private LocalDateTime wakeUpTime;

    /**
     * 睡眠时长（小时）
     */
    private Double duration;

    /**
     * 睡眠质量（1-5星）
     */
    private Integer quality;

    /**
     * 描述
     */
    private String description;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalDateTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public LocalDateTime getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(LocalDateTime wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}