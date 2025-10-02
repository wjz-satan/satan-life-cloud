package com.satan.life.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_task")
public class Task extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 任务标题
     */
    private String title;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 任务状态（0：待处理，1：进行中，2：已完成，3：已逾期）
     */
    private Integer status;
    
    /**
     * 优先级（1：低，2：中，3：高）
     */
    private Integer priority;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 截止时间
     */
    private LocalDateTime endTime;
    
    /**
     * 完成时间
     */
    private LocalDateTime completionTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 标签ID列表（逗号分隔）
     */
    private String tagIds;
    
    /**
     * 是否重复任务
     */
    private Boolean recurring;
    
    /**
     * 重复规则（如：daily, weekly, monthly）
     */
    private String recurrenceRule;
    
    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;
    
    /**
     * 标签列表
     */
    @TableField(exist = false)
    private List<String> tagList;
}