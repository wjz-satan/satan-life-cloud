package com.satan.life.note.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.satan.life.note.entity.NoteCategory;
import com.satan.life.note.mapper.NoteCategoryMapper;
import com.satan.life.note.service.NoteCategoryService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Map;

/**
 * 笔记分类服务实现类
 */
@Service
public class NoteCategoryServiceImpl extends ServiceImpl<NoteCategoryMapper, NoteCategory> implements NoteCategoryService {

    @Override
    public R<?> getCategoryList(Long userId) {
        if (userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        QueryWrapper<NoteCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("sort");
        wrapper.orderByDesc("create_time");
        
        List<NoteCategory> categories = baseMapper.selectList(wrapper);
        return R.success(categories);
    }

    @Override
    public R<?> addCategory(NoteCategory category) {
        if (category == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (category.getUserId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (!StringUtils.hasText(category.getName())) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查分类名称是否已存在
        QueryWrapper<NoteCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", category.getUserId());
        wrapper.eq("name", category.getName());
        
        if (baseMapper.selectCount(wrapper) > 0) {
            return R.<Object>error(ResultCode.BUSINESS_ERROR);
        }
        
        // 设置默认排序号
        if (category.getSort() == null) {
            category.setSort(0);
        }
        
        if (baseMapper.insert(category) > 0) {
            return R.success("添加成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> updateCategory(NoteCategory category) {
        if (category == null || category.getId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查分类是否存在
        NoteCategory existingCategory = baseMapper.selectById(category.getId());
        if (existingCategory == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!existingCategory.getUserId().equals(category.getUserId())) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        // 检查分类名称是否已存在
        if (StringUtils.hasText(category.getName())) {
            QueryWrapper<NoteCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", category.getUserId());
            wrapper.eq("name", category.getName());
            wrapper.ne("id", category.getId());
            
            if (baseMapper.selectCount(wrapper) > 0) {
                return R.<Object>error(ResultCode.BUSINESS_ERROR);
            }
        }
        
        if (baseMapper.updateById(category) > 0) {
            return R.success("更新成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> deleteCategory(Long categoryId, Long userId) {
        if (categoryId == null || userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查分类是否存在
        NoteCategory category = baseMapper.selectById(categoryId);
        if (category == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!category.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        // TODO: 检查分类下是否有笔记，如果有则不允许删除
        
        if (baseMapper.deleteById(categoryId) > 0) {
            return R.success("删除成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> getCategoryDetail(Long categoryId, Long userId) {
        if (categoryId == null || userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查分类是否存在
        NoteCategory category = baseMapper.selectById(categoryId);
        if (category == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!category.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        return R.success(category);
    }
}