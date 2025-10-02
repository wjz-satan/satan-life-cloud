package com.satan.life.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.task.entity.TaskCategory;
import com.satan.life.common.result.R;

/**
 * 任务分类服务接口
 */
public interface TaskCategoryService extends IService<TaskCategory> {
    
    /**
     * 获取分类列表
     */
    R<?> getCategoryList(Long userId);
    
    /**
     * 添加分类
     */
    R<?> addCategory(TaskCategory category);
    
    /**
     * 更新分类
     */
    R<?> updateCategory(TaskCategory category);
    
    /**
     * 删除分类
     */
    R<?> deleteCategory(Long categoryId, Long userId);
    
    /**
     * 获取分类详情
     */
    R<?> getCategoryDetail(Long categoryId, Long userId);
}