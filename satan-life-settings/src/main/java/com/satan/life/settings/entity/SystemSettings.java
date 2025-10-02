package com.satan.life.settings.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;

/**
 * 系统设置实体类
 */
@Data
@TableName("system_settings")
public class SystemSettings extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @TableField("setting_key")
    private String settingKey;
    
    @TableField("setting_value")
    private String settingValue;
    
    @TableField("description")
    private String description;
    
    @TableField("setting_type")
    private String settingType;
    
    @TableField("enabled")
    private Boolean enabled;
}