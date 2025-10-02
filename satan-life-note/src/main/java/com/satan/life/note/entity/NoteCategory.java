package com.satan.life.note.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记分类实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_note_category")
public class NoteCategory extends BaseEntity {
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
     * 排序号
     */
    private Integer sort;
    
    /**
     * 笔记数量
     */
    @TableField(exist = false)
    private Integer noteCount;
}