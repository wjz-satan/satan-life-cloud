package com.satan.life.trip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 行程实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("trip")
public class Trip extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 行程名称
     */
    private String tripName;

    /**
     * 开始时间
     */
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    private LocalDateTime endDate;

    /**
     * 地点
     */
    private String location;

    /**
     * 行程描述
     */
    private String description;

    /**
     * 状态（0：未开始，1：进行中，2：已完成，3：已取消）
     */
    private Integer status;
}