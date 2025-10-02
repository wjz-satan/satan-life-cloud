package com.satan.life.settings.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.settings.entity.SystemSettings;
import com.satan.life.settings.mapper.SystemSettingsMapper;
import com.satan.life.settings.service.SystemSettingsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统设置服务实现类
 */
@Service
public class SystemSettingsServiceImpl extends ServiceImpl<SystemSettingsMapper, SystemSettings> implements SystemSettingsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemSettingsServiceImpl.class);
    
    @Override
    public SystemSettings getSystemSettingByKey(String settingKey) {
        logger.info("获取系统设置键名为{}的设置信息", settingKey);
        return baseMapper.selectBySettingKey(settingKey);
    }
    
    @Override
    public List<SystemSettings> getSystemSettingsByType(String settingType) {
        logger.info("获取系统设置类型为{}的设置列表", settingType);
        return baseMapper.selectBySettingType(settingType);
    }
    
    @Override
    public List<SystemSettings> getEnabledSystemSettings() {
        logger.info("获取所有启用的系统设置");
        return baseMapper.selectEnabledSettings();
    }
    
    @Override
    public List<SystemSettings> getSystemSettingsByKeys(List<String> settingKeys) {
        logger.info("批量获取系统设置，键名列表: {}", settingKeys);
        return baseMapper.selectBatchBySettingKeys(settingKeys);
    }
    
    @Transactional
    @Override
    public SystemSettings updateSystemSetting(SystemSettings systemSettings) {
        logger.info("更新系统设置，键名为: {}", systemSettings.getSettingKey());
        
        if (systemSettings.getId() == null) {
            SystemSettings existingSetting = getSystemSettingByKey(systemSettings.getSettingKey());
            if (existingSetting != null) {
                systemSettings.setId(existingSetting.getId());
            }
        }
        
        saveOrUpdate(systemSettings);
        return systemSettings;
    }
    
    @Transactional
    @Override
    public Boolean toggleSystemSettingStatus(Long id, Boolean enabled) {
        logger.info("切换系统设置ID为{}的启用状态为: {}", id, enabled);
        
        SystemSettings systemSettings = getById(id);
        if (systemSettings == null) {
            logger.warn("未找到ID为{}的系统设置", id);
            return false;
        }
        
        systemSettings.setEnabled(enabled);
        return updateById(systemSettings);
    }
    
    @Override
    public Map<String, String> getSystemSettingsMap() {
        logger.info("获取系统设置的键值映射");
        
        List<SystemSettings> systemSettingsList = list();
        return systemSettingsList.stream()
                .collect(Collectors.toMap(
                        SystemSettings::getSettingKey,
                        SystemSettings::getSettingValue,
                        (existing, replacement) -> existing  // 处理键冲突的情况
                ));
    }
}