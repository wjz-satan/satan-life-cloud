package com.satan.life.settings.controller;

import com.satan.life.settings.entity.SystemSettings;
import com.satan.life.settings.service.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 系统设置控制器
 */
@RestController
@RequestMapping("/api/v1/system/settings")
public class SystemSettingsController {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemSettingsController.class);
    
    @Autowired
    private SystemSettingsService systemSettingsService;
    
    /**
     * 根据键名获取系统设置
     * @param settingKey 设置键名
     * @return 系统设置信息
     */
    @GetMapping("/key")
    public SystemSettings getSystemSettingByKey(@RequestParam String settingKey) {
        logger.info("请求获取系统设置键名为{}的设置信息", settingKey);
        return systemSettingsService.getSystemSettingByKey(settingKey);
    }
    
    /**
     * 根据类型获取系统设置列表
     * @param settingType 设置类型
     * @return 系统设置列表
     */
    @GetMapping("/type")
    public List<SystemSettings> getSystemSettingsByType(@RequestParam String settingType) {
        logger.info("请求获取系统设置类型为{}的设置列表", settingType);
        return systemSettingsService.getSystemSettingsByType(settingType);
    }
    
    /**
     * 获取所有启用的系统设置
     * @return 启用的系统设置列表
     */
    @GetMapping("/enabled")
    public List<SystemSettings> getEnabledSystemSettings() {
        logger.info("请求获取所有启用的系统设置");
        return systemSettingsService.getEnabledSystemSettings();
    }
    
    /**
     * 获取系统设置的键值映射
     * @return 系统设置键值映射
     */
    @GetMapping("/map")
    public Map<String, String> getSystemSettingsMap() {
        logger.info("请求获取系统设置的键值映射");
        return systemSettingsService.getSystemSettingsMap();
    }
    
    /**
     * 保存或更新系统设置
     * @param systemSettings 系统设置信息
     * @return 保存后的系统设置信息
     */
    @PostMapping
    public SystemSettings saveOrUpdateSystemSetting(@RequestBody SystemSettings systemSettings) {
        logger.info("请求保存或更新系统设置，键名为: {}", systemSettings.getSettingKey());
        return systemSettingsService.updateSystemSetting(systemSettings);
    }
    
    /**
     * 切换系统设置的启用状态
     * @param id 设置ID
     * @param enabled 是否启用
     * @return 操作结果
     */
    @PatchMapping("/{id}/status")
    public Map<String, Boolean> toggleSystemSettingStatus(@PathVariable Long id, @RequestParam Boolean enabled) {
        logger.info("请求切换系统设置ID为{}的启用状态为: {}", id, enabled);
        
        boolean success = systemSettingsService.toggleSystemSettingStatus(id, enabled);
        Map<String, Boolean> result = new java.util.HashMap<>();
        result.put("success", success);
        return result;
    }
}