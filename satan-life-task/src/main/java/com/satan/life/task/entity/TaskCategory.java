package com.satan.life.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务分类实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_task_category")
public class TaskCategory extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 颜色
     */
    private String color;
    
    /**
     * 排序号
     */
    private Integer sort;
}