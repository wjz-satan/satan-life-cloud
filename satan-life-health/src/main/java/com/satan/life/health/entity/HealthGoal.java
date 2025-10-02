package com.satan.life.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 健康目标实体类
 */
@Data
@TableName("health_goal")
public class HealthGoal {

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
     * 目标类型：1-体重，2-血压，3-心率，4-血糖，5-步数，6-睡眠等
     */
    private Integer goalType;

    /**
     * 目标值
     */
    private Double targetValue;

    /**
     * 当前值
     */
    private Double currentValue;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 状态：1-进行中，2-已完成，3-已过期
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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