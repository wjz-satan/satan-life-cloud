package com.satan.life.note.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 笔记标签实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_note_tag")
public class NoteTag extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 使用次数
     */
    private Integer usageCount;
}