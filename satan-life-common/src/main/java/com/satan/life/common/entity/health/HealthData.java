package com.satan.life.common.entity.health;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * 健康数据实体类
 */
@TableName("health_data")
public class HealthData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 数据类型ID
     */
    private Long dataTypeId;

    /**
     * 数据值
     */
    private String dataValue;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

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

    public Long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}