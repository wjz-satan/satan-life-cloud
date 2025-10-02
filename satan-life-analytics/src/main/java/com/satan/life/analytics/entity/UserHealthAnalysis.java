package com.satan.life.analytics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户健康分析实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_health_analysis")
public class UserHealthAnalysis extends BaseEntity {
    
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
     * 平均睡眠时间(小时)
     */
    private BigDecimal avgSleepDuration;
    
    /**
     * 平均活动时长(分钟)
     */
    private Integer avgActivityMinutes;
    
    /**
     * 健康评分(0-100)
     */
    private Integer healthScore;
    
    /**
     * 健康建议
     */
    private String healthSuggestions;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}