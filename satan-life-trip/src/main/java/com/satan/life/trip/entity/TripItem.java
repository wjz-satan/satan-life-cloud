package com.satan.life.trip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 行程项目实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("trip_item")
public class TripItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行程ID
     */
    private Long tripId;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类型（如：交通、住宿、餐饮、景点等）
     */
    private String itemType;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 地点
     */
    private String location;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 状态（0：未开始，1：进行中，2：已完成，3：已取消）
     */
    private Integer status;
}