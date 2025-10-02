package com.satan.life.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.satan.life.task.entity.TaskCategory;
import com.satan.life.task.mapper.TaskCategoryMapper;
import com.satan.life.task.service.TaskCategoryService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

/**
 * 任务分类服务实现类
 */
@Service
public class TaskCategoryServiceImpl extends ServiceImpl<TaskCategoryMapper, TaskCategory> implements TaskCategoryService {

    @Override
    public R<?> getCategoryList(Long userId) {
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        QueryWrapper<TaskCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByAsc("sort");
        wrapper.orderByDesc("create_time");
        
        List<TaskCategory> categories = baseMapper.selectList(wrapper);
        return R.ok(categories);
    }

    @Override
    public R<?> addCategory(TaskCategory category) {
        if (category == null) {
            return R.error(ResultCode.PARAM_ERROR, "分类信息不能为空");
        }
        
        if (category.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        if (!StringUtils.hasText(category.getName())) {
            return R.error(ResultCode.PARAM_ERROR, "分类名称不能为空");
        }
        
        // 检查分类名称是否已存在
        QueryWrapper<TaskCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", category.getUserId());
        wrapper.eq("name", category.getName());
        
        if (baseMapper.selectCount(wrapper) > 0) {
            return R.error(ResultCode.BUSINESS_ERROR, "分类名称已存在");
        }
        
        // 设置默认排序号
        if (category.getSort() == null) {
            category.setSort(0);
        }
        
        if (baseMapper.insert(category) > 0) {
            return R.ok("添加成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "添加失败");
        }
    }

    @Override
    public R<?> updateCategory(TaskCategory category) {
        if (category == null || category.getId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "分类ID不能为空");
        }
        
        // 检查分类是否存在
        TaskCategory existingCategory = baseMapper.selectById(category.getId());
        if (existingCategory == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "分类不存在");
        }
        
        // 检查权限
        if (!existingCategory.getUserId().equals(category.getUserId())) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        // 检查分类名称是否已存在
        if (StringUtils.hasText(category.getName())) {
            QueryWrapper<TaskCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", category.getUserId());
            wrapper.eq("name", category.getName());
            wrapper.ne("id", category.getId());
            
            if (baseMapper.selectCount(wrapper) > 0) {
                return R.error(ResultCode.BUSINESS_ERROR, "分类名称已存在");
            }
        }
        
        if (baseMapper.updateById(category) > 0) {
            return R.ok("更新成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "更新失败");
        }
    }

    @Override
    public R<?> deleteCategory(Long categoryId, Long userId) {
        if (categoryId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 检查分类是否存在
        TaskCategory category = baseMapper.selectById(categoryId);
        if (category == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "分类不存在");
        }
        
        // 检查权限
        if (!category.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        // TODO: 检查分类下是否有任务，如果有则不允许删除
        
        if (baseMapper.deleteById(categoryId) > 0) {
            return R.ok("删除成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "删除失败");
        }
    }

    @Override
    public R<?> getCategoryDetail(Long categoryId, Long userId) {
        if (categoryId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 检查分类是否存在
        TaskCategory category = baseMapper.selectById(categoryId);
        if (category == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "分类不存在");
        }
        
        // 检查权限
        if (!category.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        return R.ok(category);
    }
}