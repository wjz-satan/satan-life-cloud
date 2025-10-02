package com.satan.life.settings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.settings.entity.SystemSettings;

import java.util.List;
import java.util.Map;

/**
 * 系统设置服务接口
 */
public interface SystemSettingsService extends IService<SystemSettings> {
    
    /**
     * 根据设置键名获取系统设置
     * @param settingKey 设置键名
     * @return 系统设置信息
     */
    SystemSettings getSystemSettingByKey(String settingKey);
    
    /**
     * 根据设置类型获取系统设置列表
     * @param settingType 设置类型
     * @return 系统设置列表
     */
    List<SystemSettings> getSystemSettingsByType(String settingType);
    
    /**
     * 获取所有启用的系统设置
     * @return 启用的系统设置列表
     */
    List<SystemSettings> getEnabledSystemSettings();
    
    /**
     * 批量获取系统设置
     * @param settingKeys 设置键名列表
     * @return 系统设置列表
     */
    List<SystemSettings> getSystemSettingsByKeys(List<String> settingKeys);
    
    /**
     * 更新系统设置
     * @param systemSettings 系统设置信息
     * @return 更新后的系统设置
     */
    SystemSettings updateSystemSetting(SystemSettings systemSettings);
    
    /**
     * 切换系统设置的启用状态
     * @param id 设置ID
     * @param enabled 是否启用
     * @return 操作是否成功
     */
    Boolean toggleSystemSettingStatus(Long id, Boolean enabled);
    
    /**
     * 获取系统设置的键值映射
     * @return 系统设置键值映射
     */
    Map<String, String> getSystemSettingsMap();
}