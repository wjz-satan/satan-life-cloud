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
 * 用户任务完成分析实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_task_analysis")
public class UserTaskAnalysis extends BaseEntity {
    
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
     * 总任务数
     */
    private Integer totalTasks;
    
    /**
     * 完成任务数
     */
    private Integer completedTasks;
    
    /**
     * 完成率(%)
     */
    private BigDecimal completionRate;
    
    /**
     * 任务类型统计
     */
    private JsonNode taskTypeStats;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}