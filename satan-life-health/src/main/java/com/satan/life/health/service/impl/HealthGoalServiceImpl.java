package com.satan.life.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.health.entity.HealthGoal;
import com.satan.life.health.mapper.HealthGoalMapper;
import com.satan.life.health.service.HealthGoalService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 健康目标服务实现类
 */
@Service
public class HealthGoalServiceImpl extends ServiceImpl<HealthGoalMapper, HealthGoal> implements HealthGoalService {

    @Resource
    private HealthGoalMapper healthGoalMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addGoal(HealthGoal healthGoal) {
        // 校验参数
        if (healthGoal == null || healthGoal.getUserId() == null || healthGoal.getGoalType() == null || healthGoal.getTargetValue() == null || healthGoal.getStartDate() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 设置默认值
        if (healthGoal.getStatus() == null) {
            healthGoal.setStatus(1); // 默认进行中
        }
        healthGoal.setCreateTime(LocalDateTime.now());
        healthGoal.setUpdateTime(LocalDateTime.now());
        healthGoal.setCreateBy(healthGoal.getUserId());
        healthGoal.setUpdateBy(healthGoal.getUserId());
        healthGoal.setDeleted(0);

        // 保存目标
        int result = healthGoalMapper.insert(healthGoal);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateGoal(HealthGoal healthGoal) {
        // 校验参数
        if (healthGoal == null || healthGoal.getId() == null || healthGoal.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查目标是否存在
        HealthGoal existGoal = healthGoalMapper.selectById(healthGoal.getId());
        if (existGoal == null || existGoal.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限修改
        if (!existGoal.getUserId().equals(healthGoal.getUserId())) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 更新目标
        HealthGoal updateGoal = new HealthGoal();
        updateGoal.setId(healthGoal.getId());
        updateGoal.setGoalType(healthGoal.getGoalType());
        updateGoal.setTargetValue(healthGoal.getTargetValue());
        updateGoal.setCurrentValue(healthGoal.getCurrentValue());
        updateGoal.setStartDate(healthGoal.getStartDate());
        updateGoal.setEndDate(healthGoal.getEndDate());
        updateGoal.setStatus(healthGoal.getStatus());
        updateGoal.setRemark(healthGoal.getRemark());
        updateGoal.setUpdateTime(LocalDateTime.now());
        updateGoal.setUpdateBy(healthGoal.getUserId());

        int result = healthGoalMapper.updateById(updateGoal);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteGoal(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查目标是否存在
        HealthGoal existGoal = healthGoalMapper.selectById(id);
        if (existGoal == null || existGoal.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 逻辑删除
        HealthGoal deleteGoal = new HealthGoal();
        deleteGoal.setId(id);
        deleteGoal.setDeleted(1);
        deleteGoal.setUpdateTime(LocalDateTime.now());

        int result = healthGoalMapper.updateById(deleteGoal);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<?> getGoalById(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询目标
        HealthGoal goal = healthGoalMapper.selectById(id);
        if (goal == null || goal.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        return R.success(goal);
    }

    @Override
    public R<?> getGoalList(Long userId, Integer goalType, Integer status) {
        // 校验参数
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        QueryWrapper<HealthGoal> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("deleted", 0);

        if (goalType != null) {
            wrapper.eq("goal_type", goalType);
        }

        if (status != null) {
            wrapper.eq("status", status);
        }

        // 按创建时间倒序排列
        wrapper.orderByDesc("create_time");

        // 查询目标列表
        List<HealthGoal> goalList = healthGoalMapper.selectList(wrapper);
        return R.success(goalList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateGoalProgress(Long id, Double currentValue) {
        // 校验参数
        if (id == null || currentValue == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查目标是否存在
        HealthGoal existGoal = healthGoalMapper.selectById(id);
        if (existGoal == null || existGoal.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查目标状态
        if (existGoal.getStatus() != 1) {
            return R.error(501, "目标状态不允许更新进度");
        }

        // 更新进度
        HealthGoal updateGoal = new HealthGoal();
        updateGoal.setId(id);
        updateGoal.setCurrentValue(currentValue);
        updateGoal.setUpdateTime(LocalDateTime.now());

        // 如果目标已达成，更新状态
        if (isGoalAchieved(existGoal, currentValue)) {
            updateGoal.setStatus(2); // 已完成
        }

        int result = healthGoalMapper.updateById(updateGoal);
        if (result > 0) {
            return R.success("进度更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> completeGoal(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查目标是否存在
        HealthGoal existGoal = healthGoalMapper.selectById(id);
        if (existGoal == null || existGoal.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查目标状态
        if (existGoal.getStatus() != 1) {
            return R.error(501, "目标状态不允许完成");
        }

        // 完成目标
        HealthGoal completeGoal = new HealthGoal();
        completeGoal.setId(id);
        completeGoal.setStatus(2); // 已完成
        completeGoal.setUpdateTime(LocalDateTime.now());

        int result = healthGoalMapper.updateById(completeGoal);
        if (result > 0) {
            return R.success("目标完成成功");
        }
        return R.error(ResultCode.ERROR);
    }

    /**
     * 判断目标是否已达成
     * @param goal 健康目标
     * @param currentValue 当前值
     * @return 是否达成
     */
    private boolean isGoalAchieved(HealthGoal goal, Double currentValue) {
        // 根据目标类型判断达成条件
        switch (goal.getGoalType()) {
            case 1: // 体重目标（通常是减重）
                return currentValue <= goal.getTargetValue();
            case 5: // 步数目标（通常是增加步数）
                return currentValue >= goal.getTargetValue();
            default:
                // 其他类型根据具体业务逻辑判断
                return currentValue.equals(goal.getTargetValue());
        }
    }
}