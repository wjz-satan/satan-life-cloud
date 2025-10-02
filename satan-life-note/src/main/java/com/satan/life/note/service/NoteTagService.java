package com.satan.life.note.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.note.entity.NoteTag;
import com.satan.life.common.result.R;
import java.util.List;

/**
 * 笔记标签服务接口
 */
public interface NoteTagService extends IService<NoteTag> {
    
    /**
     * 获取用户标签列表
     */
    R<?> getUserTagList(Long userId);
    
    /**
     * 添加标签
     */
    R<?> addTag(NoteTag tag);
    
    /**
     * 更新标签
     */
    R<?> updateTag(NoteTag tag);
    
    /**
     * 删除标签
     */
    R<?> deleteTag(Long tagId, Long userId);
    
    /**
     * 批量添加标签
     */
    R<?> batchAddTags(Long userId, List<String> tagNames);
}