package com.satan.life.analytics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户消费分析实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_spending_analysis")
public class UserSpendingAnalysis extends BaseEntity {
    
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
     * 分析日期
     */
    private LocalDate analysisDate;
    
    /**
     * 总消费金额
     */
    private BigDecimal totalSpending;
    
    /**
     * 分类消费统计
     */
    private JsonNode categorySpending;
    
    /**
     * 环比变化
     */
    private BigDecimal monthlyComparison;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}