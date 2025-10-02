package com.satan.life.settings.controller;

import com.satan.life.settings.entity.UserSettings;
import com.satan.life.settings.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 用户设置控制器
 */
@RestController
@RequestMapping("/api/v1/user/settings")
public class UserSettingsController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsController.class);
    
    @Autowired
    private UserSettingsService userSettingsService;
    
    /**
     * 获取当前用户的所有设置信息
     * @param userId 用户ID
     * @return 用户设置列表
     */
    @GetMapping
    public List<UserSettings> getUserSettings(@RequestParam Long userId) {
        logger.info("请求获取用户ID为{}的所有设置信息", userId);
        return userSettingsService.getUserSettingsByUserId(userId);
    }
    
    /**
     * 根据键名获取用户设置
     * @param userId 用户ID
     * @param settingKey 设置键名
     * @return 用户设置信息
     */
    @GetMapping("/key")
    public UserSettings getUserSettingByKey(@RequestParam Long userId, @RequestParam String settingKey) {
        logger.info("请求获取用户ID为{}的设置项{}", userId, settingKey);
        return userSettingsService.getUserSettingByUserIdAndKey(userId, settingKey);
    }
    
    /**
     * 保存或更新单个用户设置
     * @param userSettings 用户设置信息
     * @return 保存后的用户设置信息
     */
    @PostMapping
    public UserSettings saveOrUpdateUserSetting(@RequestBody UserSettings userSettings) {
        logger.info("请求保存或更新用户ID为{}的设置项{}", userSettings.getUserId(), userSettings.getSettingKey());
        return userSettingsService.saveOrUpdateUserSetting(userSettings);
    }
    
    /**
     * 批量保存或更新用户设置
     * @param userSettingsList 用户设置列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public Map<String, Boolean> saveOrUpdateUserSettingsBatch(@RequestBody List<UserSettings> userSettingsList) {
        logger.info("请求批量保存或更新用户设置，数量: {}", userSettingsList.size());
        
        boolean success = userSettingsService.saveOrUpdateUserSettingsBatch(userSettingsList);
        Map<String, Boolean> result = new java.util.HashMap<>();
        result.put("success", success);
        return result;
    }
    
    /**
     * 初始化用户默认设置
     * @param userId 用户ID
     * @return 初始化后的用户设置列表
     */
    @PostMapping("/init")
    public List<UserSettings> initDefaultSettings(@RequestParam Long userId) {
        logger.info("请求为用户ID为{}初始化默认设置", userId);
        return userSettingsService.initDefaultSettings(userId);
    }
    
    /**
     * 更新用户的特定设置项
     * @param userId 用户ID
     * @param settingKey 设置键名
     * @param settingValue 设置值
     * @return 更新后的用户设置信息
     */
    @PatchMapping
    public UserSettings updateUserSettingItem(@RequestParam Long userId, @RequestParam String settingKey, @RequestParam String settingValue) {
        logger.info("请求更新用户ID为{}的设置项{}，新值: {}", userId, settingKey, settingValue);
        return userSettingsService.updateUserSettingItem(userId, settingKey, settingValue);
    }
    
    /**
     * 获取用户设置的键值映射
     * @param userId 用户ID
     * @return 用户设置键值映射
     */
    @GetMapping("/map")
    public Map<String, String> getUserSettingsMap(@RequestParam Long userId) {
        logger.info("请求获取用户ID为{}的设置键值映射", userId);
        return userSettingsService.getUserSettingsMap(userId);
    }
}