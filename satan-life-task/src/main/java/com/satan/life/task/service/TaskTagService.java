package com.satan.life.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.task.entity.TaskTag;
import com.satan.life.common.result.R;
import java.util.List;

/**
 * 任务标签服务接口
 */
public interface TaskTagService extends IService<TaskTag> {
    
    /**
     * 获取用户标签列表
     */
    R<?> getUserTagList(Long userId);
    
    /**
     * 添加标签
     */
    R<?> addTag(TaskTag tag);
    
    /**
     * 更新标签
     */
    R<?> updateTag(TaskTag tag);
    
    /**
     * 删除标签
     */
    R<?> deleteTag(Long tagId, Long userId);
    
    /**
     * 批量添加标签
     */
    R<?> batchAddTags(Long userId, List<String> tagNames);
}