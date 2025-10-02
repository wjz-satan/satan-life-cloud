package com.satan.life.health.controller;

import com.satan.life.health.entity.HealthReminder;
import com.satan.life.health.service.HealthReminderService;
import com.satan.life.common.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 健康提醒控制器
 */
@RestController
@RequestMapping("/api/health/reminder")
public class HealthReminderController {

    @Resource
    private HealthReminderService healthReminderService;

    /**
     * 添加健康提醒
     */
    @PostMapping("/add/{userId}")
    public R<?> addReminder(@PathVariable Long userId, @RequestBody HealthReminder healthReminder) {
        healthReminder.setUserId(userId);
        return healthReminderService.addReminder(healthReminder);
    }

    /**
     * 更新健康提醒
     */
    @PutMapping("/update/{userId}")
    public R<?> updateReminder(@PathVariable Long userId, @RequestBody HealthReminder healthReminder) {
        healthReminder.setUserId(userId);
        return healthReminderService.updateReminder(healthReminder);
    }

    /**
     * 删除健康提醒
     */
    @DeleteMapping("/delete/{id}")
    public R<?> deleteReminder(@PathVariable Long id) {
        return healthReminderService.deleteReminder(id);
    }

    /**
     * 获取健康提醒详情
     */
    @GetMapping("/detail/{id}")
    public R<?> getReminderById(@PathVariable Long id) {
        return healthReminderService.getReminderById(id);
    }

    /**
     * 查询用户健康提醒列表
     */
    @GetMapping("/list/{userId}")
    public R<?> getReminderList(@PathVariable Long userId, 
                             @RequestParam(required = false) Integer reminderType, 
                             @RequestParam(required = false) Integer status) {
        return healthReminderService.getReminderList(userId, reminderType, status);
    }

    /**
     * 开启/关闭健康提醒
     */
    @PutMapping("/toggle/{id}")
    public R<?> toggleReminder(@PathVariable Long id, 
                             @RequestParam Integer status) {
        return healthReminderService.toggleReminder(id, status);
    }
}