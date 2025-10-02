package com.satan.life.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.task.entity.Task;
import com.satan.life.common.result.R;
import java.util.Map;
import java.time.LocalDate;

/**
 * 任务服务接口
 */
public interface TaskService extends IService<Task> {
    
    /**
     * 添加任务
     */
    R<?> addTask(Task task);
    
    /**
     * 更新任务
     */
    R<?> updateTask(Task task);
    
    /**
     * 删除任务
     */
    R<?> deleteTask(Long taskId, Long userId);
    
    /**
     * 获取任务详情
     */
    R<?> getTaskDetail(Long taskId, Long userId);
    
    /**
     * 获取任务列表
     */
    R<?> getTaskList(Map<String, Object> params);
    
    /**
     * 更新任务状态
     */
    R<?> updateTaskStatus(Long taskId, Long userId, Integer status);
    
    /**
     * 获取日历视图任务
     */
    R<?> getCalendarViewTasks(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 搜索任务
     */
    R<?> searchTasks(Map<String, Object> params);
    
    /**
     * 批量更新任务完成状态
     */
    R<?> batchUpdateTaskStatus(Map<String, Object> params);
}