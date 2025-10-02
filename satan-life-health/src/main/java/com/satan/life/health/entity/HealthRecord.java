package com.satan.life.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 健康记录实体类
 */
@Data
@TableName("health_record")
public class HealthRecord {

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
     * 记录类型：1-体重，2-血压，3-心率，4-血糖，5-步数，6-睡眠等
     */
    private Integer recordType;

    /**
     * 记录值
     */
    private Double value;

    /**
     * 单位
     */
    private String unit;

    /**
     * 备注
     */
    private String remark;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

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