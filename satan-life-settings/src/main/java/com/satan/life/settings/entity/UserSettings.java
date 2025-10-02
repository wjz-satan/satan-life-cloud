package com.satan.life.settings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;

/**
 * 用户设置实体类
 */
@Data
@TableName("user_setting")
public class UserSettings extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("setting_key")
    private String settingKey;
    
    @TableField("setting_value")
    private String settingValue;
    
    @TableField("description")
    private String description;
}