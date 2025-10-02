package com.satan.life.settings.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.settings.entity.UserSettings;
import com.satan.life.settings.mapper.UserSettingsMapper;
import com.satan.life.settings.service.UserSettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用户设置服务实现类
 */
@Service
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, UserSettings> implements UserSettingsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public List<UserSettings> getUserSettingsByUserId(Long userId) {
        logger.info("获取用户ID为{}的所有设置信息", userId);
        return baseMapper.selectByUserId(userId);
    }
    
    @Override
    public UserSettings getUserSettingByUserIdAndKey(Long userId, String settingKey) {
        logger.info("获取用户ID为{}的设置项{}", userId, settingKey);
        return baseMapper.selectByUserIdAndKey(userId, settingKey);
    }
    
    @Transactional
    @Override
    public UserSettings saveOrUpdateUserSetting(UserSettings userSettings) {
        logger.info("保存或更新用户ID为{}的设置项{}", userSettings.getUserId(), userSettings.getSettingKey());
        
        // 检查是否已存在该用户的同键设置
        UserSettings existingSetting = getUserSettingByUserIdAndKey(
                userSettings.getUserId(), 
                userSettings.getSettingKey()
        );
        
        if (existingSetting != null) {
            userSettings.setId(existingSetting.getId());
        }
        
        saveOrUpdate(userSettings);
        return userSettings;
    }
    
    @Transactional
    @Override
    public Boolean saveOrUpdateUserSettingsBatch(List<UserSettings> userSettingsList) {
        logger.info("批量保存或更新用户设置，数量: {}", userSettingsList.size());
        
        for (UserSettings setting : userSettingsList) {
            // 检查是否已存在该用户的同键设置
            UserSettings existingSetting = getUserSettingByUserIdAndKey(
                    setting.getUserId(), 
                    setting.getSettingKey()
            );
            
            if (existingSetting != null) {
                setting.setId(existingSetting.getId());
            }
        }
        
        return saveOrUpdateBatch(userSettingsList);
    }
    
    @Transactional
    @Override
    public List<UserSettings> initDefaultSettings(Long userId) {
        logger.info("为用户ID为{}初始化默认设置", userId);
        
        // 检查是否已存在设置
        List<UserSettings> existingSettings = getUserSettingsByUserId(userId);
        if (existingSettings != null && !existingSettings.isEmpty()) {
            logger.info("用户ID为{}已存在设置信息，无需初始化", userId);
            return existingSettings;
        }
        
        List<UserSettings> defaultSettingsList = new ArrayList<>();
        
        // 创建默认设置项
        try {
            // 语言设置
            UserSettings languageSetting = new UserSettings();
            languageSetting.setUserId(userId);
            languageSetting.setSettingKey("language");
            languageSetting.setSettingValue("zh-CN");
            languageSetting.setDescription("界面语言");
            defaultSettingsList.add(languageSetting);
            
            // 时区设置
            UserSettings timezoneSetting = new UserSettings();
            timezoneSetting.setUserId(userId);
            timezoneSetting.setSettingKey("timeZone");
            timezoneSetting.setSettingValue("Asia/Shanghai");
            timezoneSetting.setDescription("时区设置");
            defaultSettingsList.add(timezoneSetting);
            
            // 日期格式设置
            UserSettings dateFormatSetting = new UserSettings();
            dateFormatSetting.setUserId(userId);
            dateFormatSetting.setSettingKey("dateFormat");
            dateFormatSetting.setSettingValue("yyyy-MM-dd");
            dateFormatSetting.setDescription("日期格式");
            defaultSettingsList.add(dateFormatSetting);
            
            // 时间格式设置
            UserSettings timeFormatSetting = new UserSettings();
            timeFormatSetting.setUserId(userId);
            timeFormatSetting.setSettingKey("timeFormat");
            timeFormatSetting.setSettingValue("HH:mm:ss");
            timeFormatSetting.setDescription("时间格式");
            defaultSettingsList.add(timeFormatSetting);
            
            // 主题设置
            UserSettings themeSetting = new UserSettings();
            themeSetting.setUserId(userId);
            themeSetting.setSettingKey("themeId");
            themeSetting.setSettingValue("1");
            themeSetting.setDescription("主题ID");
            defaultSettingsList.add(themeSetting);
            
            // 深色模式设置
            UserSettings darkModeSetting = new UserSettings();
            darkModeSetting.setUserId(userId);
            darkModeSetting.setSettingKey("darkModeEnabled");
            darkModeSetting.setSettingValue("false");
            darkModeSetting.setDescription("是否启用深色模式");
            defaultSettingsList.add(darkModeSetting);
            
            // 隐私设置
            UserSettings privacySetting = new UserSettings();
            privacySetting.setUserId(userId);
            privacySetting.setSettingKey("privacySettings");
            Map<String, Object> privacyMap = new HashMap<>();
            privacyMap.put("profileVisibility", "public");
            privacyMap.put("dataSharing", false);
            privacySetting.setSettingValue(objectMapper.writeValueAsString(privacyMap));
            privacySetting.setDescription("隐私设置");
            defaultSettingsList.add(privacySetting);
            
            // 通知设置
            UserSettings notificationSetting = new UserSettings();
            notificationSetting.setUserId(userId);
            notificationSetting.setSettingKey("notificationSettings");
            Map<String, Object> notificationMap = new HashMap<>();
            notificationMap.put("emailEnabled", true);
            notificationMap.put("smsEnabled", false);
            notificationMap.put("pushEnabled", true);
            notificationSetting.setSettingValue(objectMapper.writeValueAsString(notificationMap));
            notificationSetting.setDescription("通知设置");
            defaultSettingsList.add(notificationSetting);
            
            // 布局设置
            UserSettings layoutSetting = new UserSettings();
            layoutSetting.setUserId(userId);
            layoutSetting.setSettingKey("layoutSettings");
            Map<String, Object> layoutMap = new HashMap<>();
            layoutMap.put("sidebarCollapsed", false);
            layoutMap.put("dashboardLayout", "grid");
            layoutSetting.setSettingValue(objectMapper.writeValueAsString(layoutMap));
            layoutSetting.setDescription("布局设置");
            defaultSettingsList.add(layoutSetting);
            
            // 批量保存默认设置
            saveBatch(defaultSettingsList);
        } catch (Exception e) {
            logger.error("初始化用户默认设置失败", e);
            throw new RuntimeException("初始化用户默认设置失败", e);
        }
        
        return defaultSettingsList;
    }
    
    @Transactional
    @Override
    public UserSettings updateUserSettingItem(Long userId, String settingKey, String settingValue) {
        logger.info("更新用户ID为{}的设置项{}，新值: {}", userId, settingKey, settingValue);
        
        UserSettings userSetting = getUserSettingByUserIdAndKey(userId, settingKey);
        
        if (userSetting == null) {
            // 如果设置不存在，则创建新的设置
            userSetting = new UserSettings();
            userSetting.setUserId(userId);
            userSetting.setSettingKey(settingKey);
        }
        
        userSetting.setSettingValue(settingValue);
        saveOrUpdate(userSetting);
        
        return userSetting;
    }
    
    @Override
    public Map<String, String> getUserSettingsMap(Long userId) {
        logger.info("获取用户ID为{}的设置键值映射", userId);
        
        List<UserSettings> userSettingsList = getUserSettingsByUserId(userId);
        if (userSettingsList == null || userSettingsList.isEmpty()) {
            // 如果没有设置，初始化默认设置
            userSettingsList = initDefaultSettings(userId);
        }
        
        // 转换为键值映射
        return userSettingsList.stream()
                .collect(Collectors.toMap(
                        UserSettings::getSettingKey,
                        UserSettings::getSettingValue
                ));
    }
}