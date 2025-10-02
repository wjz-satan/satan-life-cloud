package com.satan.life.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.health.entity.HealthGoal;
import com.satan.life.common.result.R;

import java.time.LocalDate;
import java.util.List;

/**
 * 健康目标服务接口
 */
public interface HealthGoalService extends IService<HealthGoal> {

    /**
     * 添加健康目标
     * @param healthGoal 健康目标信息
     * @return 添加结果
     */
    R<?> addGoal(HealthGoal healthGoal);

    /**
     * 更新健康目标
     * @param healthGoal 健康目标信息
     * @return 更新结果
     */
    R<?> updateGoal(HealthGoal healthGoal);

    /**
     * 删除健康目标
     * @param id 目标ID
     * @return 删除结果
     */
    R<?> deleteGoal(Long id);

    /**
     * 获取健康目标详情
     * @param id 目标ID
     * @return 目标详情
     */
    R<?> getGoalById(Long id);

    /**
     * 查询用户健康目标列表
     * @param userId 用户ID
     * @param goalType 目标类型（可选）
     * @param status 状态（可选）
     * @return 目标列表
     */
    R<?> getGoalList(Long userId, Integer goalType, Integer status);

    /**
     * 更新健康目标进度
     * @param id 目标ID
     * @param currentValue 当前值
     * @return 更新结果
     */
    R<?> updateGoalProgress(Long id, Double currentValue);

    /**
     * 完成健康目标
     * @param id 目标ID
     * @return 完成结果
     */
    R<?> completeGoal(Long id);
}