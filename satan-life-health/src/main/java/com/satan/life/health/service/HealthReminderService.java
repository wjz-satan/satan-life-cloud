package com.satan.life.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.health.entity.HealthReminder;
import com.satan.life.common.result.R;

import java.util.List;

/**
 * 健康提醒服务接口
 */
public interface HealthReminderService extends IService<HealthReminder> {

    /**
     * 添加健康提醒
     * @param healthReminder 健康提醒信息
     * @return 添加结果
     */
    R<?> addReminder(HealthReminder healthReminder);

    /**
     * 更新健康提醒
     * @param healthReminder 健康提醒信息
     * @return 更新结果
     */
    R<?> updateReminder(HealthReminder healthReminder);

    /**
     * 删除健康提醒
     * @param id 提醒ID
     * @return 删除结果
     */
    R<?> deleteReminder(Long id);

    /**
     * 获取健康提醒详情
     * @param id 提醒ID
     * @return 提醒详情
     */
    R<?> getReminderById(Long id);

    /**
     * 查询用户健康提醒列表
     * @param userId 用户ID
     * @param reminderType 提醒类型（可选）
     * @param status 状态（可选）
     * @return 提醒列表
     */
    R<?> getReminderList(Long userId, Integer reminderType, Integer status);

    /**
     * 开启/关闭健康提醒
     * @param id 提醒ID
     * @param status 状态（1-开启，0-关闭）
     * @return 操作结果
     */
    R<?> toggleReminder(Long id, Integer status);
}