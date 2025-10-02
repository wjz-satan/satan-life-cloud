package com.satan.life.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务标签实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_task_tag")
public class TaskTag extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签颜色
     */
    private String color;
    
    /**
     * 使用次数
     */
    private Integer usageCount;
}