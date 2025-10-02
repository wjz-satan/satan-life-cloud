package com.satan.life.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satan.life.task.entity.Task;
import com.satan.life.task.mapper.TaskMapper;
import com.satan.life.task.service.TaskService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 任务服务实现类
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public R<?> addTask(Task task) {
        if (task == null) {
            return R.error(ResultCode.PARAM_ERROR, "任务信息不能为空");
        }
        
        if (task.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        if (!StringUtils.hasText(task.getTitle())) {
            return R.error(ResultCode.PARAM_ERROR, "任务标题不能为空");
        }
        
        // 设置默认值
        if (task.getStatus() == null) {
            task.setStatus(0); // 默认待处理
        }
        
        if (task.getPriority() == null) {
            task.setPriority(2); // 默认中优先级
        }
        
        if (task.getRecurring() == null) {
            task.setRecurring(false);
        }
        
        if (baseMapper.insert(task) > 0) {
            return R.ok("添加成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "添加失败");
        }
    }

    @Override
    public R<?> updateTask(Task task) {
        if (task == null || task.getId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "任务ID不能为空");
        }
        
        // 检查任务是否存在
        Task existingTask = baseMapper.selectById(task.getId());
        if (existingTask == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "任务不存在");
        }
        
        // 检查权限
        if (!existingTask.getUserId().equals(task.getUserId())) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        if (baseMapper.updateById(task) > 0) {
            return R.ok("更新成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "更新失败");
        }
    }

    @Override
    public R<?> deleteTask(Long taskId, Long userId) {
        if (taskId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 检查任务是否存在
        Task task = baseMapper.selectById(taskId);
        if (task == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "任务不存在");
        }
        
        // 检查权限
        if (!task.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        if (baseMapper.deleteById(taskId) > 0) {
            return R.ok("删除成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "删除失败");
        }
    }

    @Override
    public R<?> getTaskDetail(Long taskId, Long userId) {
        if (taskId == null) {
            return R.error(ResultCode.PARAM_ERROR, "任务ID不能为空");
        }
        
        // 检查任务是否存在
        Task task = baseMapper.selectById(taskId);
        if (task == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "任务不存在");
        }
        
        // 检查权限
        if (!task.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限查看");
        }
        
        // TODO: 填充分类名称和标签列表
        
        return R.ok(task);
    }

    @Override
    public R<?> getTaskList(Map<String, Object> params) {
        if (params == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        Long categoryId = params.get("categoryId") != null ? Long.valueOf(params.get("categoryId").toString()) : null;
        Integer status = params.get("status") != null ? Integer.valueOf(params.get("status").toString()) : null;
        Integer priority = params.get("priority") != null ? Integer.valueOf(params.get("priority").toString()) : null;
        Integer page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) : 1;
        Integer pageSize = params.get("pageSize") != null ? Integer.valueOf(params.get("pageSize").toString()) : 10;
        String orderBy = params.get("orderBy") != null ? params.get("orderBy").toString() : "end_time";
        String orderType = params.get("orderType") != null ? params.get("orderType").toString() : "asc";
        
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        
        if (priority != null) {
            wrapper.eq("priority", priority);
        }
        
        // 构建排序
        if ("asc".equals(orderType)) {
            wrapper.orderByAsc(orderBy);
        } else {
            wrapper.orderByDesc(orderBy);
        }
        
        Page<Task> taskPage = new Page<>(page, pageSize);
        Page<Task> resultPage = baseMapper.selectPage(taskPage, wrapper);
        
        // TODO: 填充分类名称和标签列表
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", resultPage.getTotal());
        result.put("list", resultPage.getRecords());
        
        return R.ok(result);
    }

    @Override
    public R<?> updateTaskStatus(Long taskId, Long userId, Integer status) {
        if (taskId == null || userId == null || status == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 检查状态是否合法
        if (status < 0 || status > 3) {
            return R.error(ResultCode.PARAM_ERROR, "任务状态不合法");
        }
        
        // 检查任务是否存在
        Task task = baseMapper.selectById(taskId);
        if (task == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "任务不存在");
        }
        
        // 检查权限
        if (!task.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        task.setStatus(status);
        
        // 如果是完成状态，设置完成时间
        if (status == 2) {
            task.setCompletionTime(LocalDateTime.now());
        }
        
        if (baseMapper.updateById(task) > 0) {
            return R.ok("更新成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "更新失败");
        }
    }

    @Override
    public R<?> getCalendarViewTasks(Long userId, LocalDate startDate, LocalDate endDate) {
        if (userId == null || startDate == null || endDate == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 转换为LocalDateTime进行查询
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.and(w -> w
            .between("start_time", startDateTime, endDateTime)
            .or()
            .between("end_time", startDateTime, endDateTime)
            .or()
            .and(w1 -> w1.le("start_time", startDateTime).ge("end_time", endDateTime))
        );
        
        List<Task> tasks = baseMapper.selectList(wrapper);
        
        // TODO: 填充分类名称和标签列表
        
        return R.ok(tasks);
    }

    @Override
    public R<?> searchTasks(Map<String, Object> params) {
        if (params == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        String keyword = params.get("keyword") != null ? params.get("keyword").toString() : null;
        Integer page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) : 1;
        Integer pageSize = params.get("pageSize") != null ? Integer.valueOf(params.get("pageSize").toString()) : 10;
        
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        if (!StringUtils.hasText(keyword)) {
            return R.error(ResultCode.PARAM_ERROR, "搜索关键词不能为空");
        }
        
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.and(w -> w.like("title", keyword).or().like("description", keyword));
        wrapper.orderByAsc("end_time");
        
        Page<Task> taskPage = new Page<>(page, pageSize);
        Page<Task> resultPage = baseMapper.selectPage(taskPage, wrapper);
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", resultPage.getTotal());
        result.put("list", resultPage.getRecords());
        
        return R.ok(result);
    }

    @Override
    public R<?> batchUpdateTaskStatus(Map<String, Object> params) {
        if (params == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        List<Long> taskIds = (List<Long>) params.get("taskIds");
        Integer status = params.get("status") != null ? Integer.valueOf(params.get("status").toString()) : null;
        
        if (userId == null || taskIds == null || taskIds.isEmpty() || status == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不完整");
        }
        
        // 检查状态是否合法
        if (status < 0 || status > 3) {
            return R.error(ResultCode.PARAM_ERROR, "任务状态不合法");
        }
        
        // 批量更新任务状态
        int updateCount = 0;
        
        for (Long taskId : taskIds) {
            Task task = baseMapper.selectById(taskId);
            
            if (task != null && task.getUserId().equals(userId)) {
                task.setStatus(status);
                
                // 如果是完成状态，设置完成时间
                if (status == 2) {
                    task.setCompletionTime(LocalDateTime.now());
                }
                
                if (baseMapper.updateById(task) > 0) {
                    updateCount++;
                }
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("updateCount", updateCount);
        
        return R.ok(result);
    }
}