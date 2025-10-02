package com.satan.life.note.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.note.entity.NoteCategory;
import com.satan.life.common.result.R;
import java.util.Map;

/**
 * 笔记分类服务接口
 */
public interface NoteCategoryService extends IService<NoteCategory> {
    
    /**
     * 获取分类列表
     */
    R<?> getCategoryList(Long userId);
    
    /**
     * 添加分类
     */
    R<?> addCategory(NoteCategory category);
    
    /**
     * 更新分类
     */
    R<?> updateCategory(NoteCategory category);
    
    /**
     * 删除分类
     */
    R<?> deleteCategory(Long categoryId, Long userId);
    
    /**
     * 获取分类详情
     */
    R<?> getCategoryDetail(Long categoryId, Long userId);
}