package com.satan.life.settings.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.settings.entity.UserSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户设置Mapper接口
 */
@Mapper
public interface UserSettingsMapper extends BaseMapper<UserSettings> {
    
    /**
     * 根据用户ID查询用户设置
     * @param userId 用户ID
     * @return 用户设置列表
     */
    List<UserSettings> selectByUserId(Long userId);
    
    /**
     * 根据用户ID和设置键查询设置
     * @param userId 用户ID
     * @param settingKey 设置键
     * @return 用户设置信息
     */
    UserSettings selectByUserIdAndKey(@Param("userId") Long userId, @Param("settingKey") String settingKey);
    
    /**
     * 批量查询用户设置
     * @param userIds 用户ID列表
     * @return 用户设置列表
     */
    List<UserSettings> selectBatchByUserIds(@Param("userIds") List<Long> userIds);
}