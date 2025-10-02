package com.satan.life.settings.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.settings.entity.SystemSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统设置Mapper接口
 */
@Mapper
public interface SystemSettingsMapper extends BaseMapper<SystemSettings> {
    
    /**
     * 根据设置键名查询系统设置
     * @param settingKey 设置键名
     * @return 系统设置信息
     */
    SystemSettings selectBySettingKey(String settingKey);
    
    /**
     * 根据设置类型查询系统设置
     * @param settingType 设置类型
     * @return 系统设置列表
     */
    List<SystemSettings> selectBySettingType(String settingType);
    
    /**
     * 查询所有启用的系统设置
     * @return 启用的系统设置列表
     */
    List<SystemSettings> selectEnabledSettings();
    
    /**
     * 批量查询系统设置
     * @param settingKeys 设置键名列表
     * @return 系统设置列表
     */
    List<SystemSettings> selectBatchBySettingKeys(@Param("settingKeys") List<String> settingKeys);
}