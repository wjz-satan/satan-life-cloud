package com.satan.life.settings.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.settings.entity.UserSettings;

import java.util.List;
import java.util.Map;

/**
 * 用户设置服务接口
 */
public interface UserSettingsService extends IService<UserSettings> {
    
    /**
     * 根据用户ID获取所有用户设置
     * @param userId 用户ID
     * @return 用户设置列表
     */
    List<UserSettings> getUserSettingsByUserId(Long userId);
    
    /**
     * 根据用户ID和设置键获取用户设置
     * @param userId 用户ID
     * @param settingKey 设置键
     * @return 用户设置信息
     */
    UserSettings getUserSettingByUserIdAndKey(Long userId, String settingKey);
    
    /**
     * 保存或更新用户设置
     * @param userSettings 用户设置信息
     * @return 保存后的用户设置信息
     */
    UserSettings saveOrUpdateUserSetting(UserSettings userSettings);
    
    /**
     * 批量保存或更新用户设置
     * @param userSettingsList 用户设置列表
     * @return 操作是否成功
     */
    Boolean saveOrUpdateUserSettingsBatch(List<UserSettings> userSettingsList);
    
    /**
     * 根据用户ID初始化默认设置
     * @param userId 用户ID
     * @return 初始化后的用户设置列表
     */
    List<UserSettings> initDefaultSettings(Long userId);
    
    /**
     * 更新用户的特定设置项
     * @param userId 用户ID
     * @param settingKey 设置键
     * @param settingValue 设置值
     * @return 更新后的用户设置信息
     */
    UserSettings updateUserSettingItem(Long userId, String settingKey, String settingValue);
    
    /**
     * 根据用户ID获取设置的键值映射
     * @param userId 用户ID
     * @return 设置的键值映射
     */
    Map<String, String> getUserSettingsMap(Long userId);
}