package com.satan.life.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.health.entity.HealthReminder;
import com.satan.life.health.mapper.HealthReminderMapper;
import com.satan.life.health.service.HealthReminderService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 健康提醒服务实现类
 */
@Service
public class HealthReminderServiceImpl extends ServiceImpl<HealthReminderMapper, HealthReminder> implements HealthReminderService {

    @Resource
    private HealthReminderMapper healthReminderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addReminder(HealthReminder healthReminder) {
        // 校验参数
        if (healthReminder == null || healthReminder.getUserId() == null || healthReminder.getReminderType() == null || healthReminder.getRemindTime() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 设置默认值
        if (healthReminder.getStatus() == null) {
            healthReminder.setStatus(1); // 默认开启
        }
        if (healthReminder.getRepeatType() == null) {
            healthReminder.setRepeatType(0); // 默认不重复
        }
        healthReminder.setCreateTime(LocalDateTime.now());
        healthReminder.setUpdateTime(LocalDateTime.now());
        healthReminder.setCreateBy(healthReminder.getUserId());
        healthReminder.setUpdateBy(healthReminder.getUserId());
        healthReminder.setDeleted(0);

        // 保存提醒
        int result = healthReminderMapper.insert(healthReminder);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateReminder(HealthReminder healthReminder) {
        // 校验参数
        if (healthReminder == null || healthReminder.getId() == null || healthReminder.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查提醒是否存在
        HealthReminder existReminder = healthReminderMapper.selectById(healthReminder.getId());
        if (existReminder == null || existReminder.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限修改
        if (!existReminder.getUserId().equals(healthReminder.getUserId())) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 更新提醒
        HealthReminder updateReminder = new HealthReminder();
        updateReminder.setId(healthReminder.getId());
        updateReminder.setReminderType(healthReminder.getReminderType());
        updateReminder.setRemindTime(healthReminder.getRemindTime());
        updateReminder.setRepeatType(healthReminder.getRepeatType());
        updateReminder.setStatus(healthReminder.getStatus());
        updateReminder.setContent(healthReminder.getContent());
        updateReminder.setUpdateTime(LocalDateTime.now());
        updateReminder.setUpdateBy(healthReminder.getUserId());

        int result = healthReminderMapper.updateById(updateReminder);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteReminder(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查提醒是否存在
        HealthReminder existReminder = healthReminderMapper.selectById(id);
        if (existReminder == null || existReminder.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 逻辑删除
        HealthReminder deleteReminder = new HealthReminder();
        deleteReminder.setId(id);
        deleteReminder.setDeleted(1);
        deleteReminder.setUpdateTime(LocalDateTime.now());

        int result = healthReminderMapper.updateById(deleteReminder);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<?> getReminderById(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询提醒
        HealthReminder reminder = healthReminderMapper.selectById(id);
        if (reminder == null || reminder.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        return R.success(reminder);
    }

    @Override
    public R<?> getReminderList(Long userId, Integer reminderType, Integer status) {
        // 校验参数
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        QueryWrapper<HealthReminder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("deleted", 0);

        if (reminderType != null) {
            wrapper.eq("reminder_type", reminderType);
        }

        if (status != null) {
            wrapper.eq("status", status);
        }

        // 按提醒时间排序
        wrapper.orderByAsc("reminder_time");

        // 查询提醒列表
        List<HealthReminder> reminderList = healthReminderMapper.selectList(wrapper);
        return R.success(reminderList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> toggleReminder(Long id, Integer status) {
        // 校验参数
        if (id == null || status == null || (status != 0 && status != 1)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查提醒是否存在
        HealthReminder existReminder = healthReminderMapper.selectById(id);
        if (existReminder == null || existReminder.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 更新状态
        HealthReminder toggleReminder = new HealthReminder();
        toggleReminder.setId(id);
        toggleReminder.setStatus(status);
        toggleReminder.setUpdateTime(LocalDateTime.now());

        int result = healthReminderMapper.updateById(toggleReminder);
        if (result > 0) {
            return R.success("操作成功");
        }
        return R.error(ResultCode.ERROR);
    }
}