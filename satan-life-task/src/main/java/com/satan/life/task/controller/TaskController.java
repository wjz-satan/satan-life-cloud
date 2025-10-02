package com.satan.life.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.satan.life.task.service.TaskService;
import com.satan.life.common.result.R;

import java.time.LocalDate;
import java.util.Map;

/**
 * 任务控制器
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 添加任务
     */
    @PostMapping
    public R<?> addTask(@RequestBody Map<String, Object> params) {
        return taskService.addTask(params);
    }

    /**
     * 更新任务
     */
    @PutMapping
    public R<?> updateTask(@RequestBody Map<String, Object> params) {
        return taskService.updateTask(params);
    }

    /**
     * 删除任务
     */
    @DeleteMapping
    public R<?> deleteTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.deleteTask(taskId, userId);
    }

    /**
     * 获取任务详情
     */
    @GetMapping
    public R<?> getTaskDetail(@RequestParam Long taskId, @RequestParam Long userId) {
        return taskService.getTaskDetail(taskId, userId);
    }

    /**
     * 获取任务列表
     */
    @GetMapping("/list")
    public R<?> getTaskList(@RequestParam Map<String, Object> params) {
        return taskService.getTaskList(params);
    }

    /**
     * 更新任务状态
     */
    @PutMapping("/status")
    public R<?> updateTaskStatus(@RequestBody Map<String, Object> params) {
        Long taskId = params.get("taskId") != null ? Long.valueOf(params.get("taskId").toString()) : null;
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        Integer status = params.get("status") != null ? Integer.valueOf(params.get("status").toString()) : null;
        
        return taskService.updateTaskStatus(taskId, userId, status);
    }

    /**
     * 获取日历视图任务
     */
    @GetMapping("/calendar")
    public R<?> getCalendarViewTasks(@RequestParam Long userId, 
                                    @RequestParam String startDate, 
                                    @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        return taskService.getCalendarViewTasks(userId, start, end);
    }

    /**
     * 搜索任务
     */
    @GetMapping("/search")
    public R<?> searchTasks(@RequestParam Map<String, Object> params) {
        return taskService.searchTasks(params);
    }

    /**
     * 批量更新任务状态
     */
    @PutMapping("/batch/status")
    public R<?> batchUpdateTaskStatus(@RequestBody Map<String, Object> params) {
        return taskService.batchUpdateTaskStatus(params);
    }
}