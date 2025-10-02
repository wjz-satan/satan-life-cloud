package com.satan.life.note.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 笔记实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_note")
public class Note extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 笔记标题
     */
    private String title;
    
    /**
     * 笔记内容
     */
    private String content;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 标签ID列表（逗号分隔）
     */
    private String tagIds;
    
    /**
     * 是否置顶
     */
    private Boolean top;
    
    /**
     * 访问类型（0：公开，1：私有）
     */
    private Integer accessType;
    
    /**
     * 阅读量
     */
    private Integer readCount;
    
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