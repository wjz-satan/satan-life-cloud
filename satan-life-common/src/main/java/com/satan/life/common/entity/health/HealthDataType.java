package com.satan.life.common.entity.health;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

/**
 * 健康数据类型实体类
 */
@TableName("health_data_type")
public class HealthDataType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 类型名称（如：体重、血压、心率等）
     */
    private String typeName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 描述
     */
    private String description;

    /**
     * 正常范围
     */
    private String normalRange;

    // getter and setter methods
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }
}