package com.satan.life.health.controller;

import com.satan.life.health.entity.HealthGoal;
import com.satan.life.health.service.HealthGoalService;
import com.satan.life.common.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 健康目标控制器
 */
@RestController
@RequestMapping("/api/health/goal")
public class HealthGoalController {

    @Resource
    private HealthGoalService healthGoalService;

    /**
     * 添加健康目标
     */
    @PostMapping("/add/{userId}")
    public R<?> addGoal(@PathVariable Long userId, @RequestBody HealthGoal healthGoal) {
        healthGoal.setUserId(userId);
        return healthGoalService.addGoal(healthGoal);
    }

    /**
     * 更新健康目标
     */
    @PutMapping("/update/{userId}")
    public R<?> updateGoal(@PathVariable Long userId, @RequestBody HealthGoal healthGoal) {
        healthGoal.setUserId(userId);
        return healthGoalService.updateGoal(healthGoal);
    }

    /**
     * 删除健康目标
     */
    @DeleteMapping("/delete/{id}")
    public R<?> deleteGoal(@PathVariable Long id) {
        return healthGoalService.deleteGoal(id);
    }

    /**
     * 获取健康目标详情
     */
    @GetMapping("/detail/{id}")
    public R<?> getGoalById(@PathVariable Long id) {
        return healthGoalService.getGoalById(id);
    }

    /**
     * 查询用户健康目标列表
     */
    @GetMapping("/list/{userId}")
    public R<?> getGoalList(@PathVariable Long userId, 
                          @RequestParam(required = false) Integer goalType, 
                          @RequestParam(required = false) Integer status) {
        return healthGoalService.getGoalList(userId, goalType, status);
    }

    /**
     * 更新健康目标进度
     */
    @PutMapping("/progress/{id}")
    public R<?> updateGoalProgress(@PathVariable Long id, 
                                  @RequestParam Double currentValue) {
        return healthGoalService.updateGoalProgress(id, currentValue);
    }

    /**
     * 完成健康目标
     */
    @PutMapping("/complete/{id}")
    public R<?> completeGoal(@PathVariable Long id) {
        return healthGoalService.completeGoal(id);
    }
}