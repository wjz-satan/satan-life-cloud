package com.satan.life.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satan.life.task.entity.TaskTag;
import com.satan.life.task.mapper.TaskTagMapper;
import com.satan.life.task.service.TaskTagService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 任务标签服务实现类
 */
@Service
public class TaskTagServiceImpl extends ServiceImpl<TaskTagMapper, TaskTag> implements TaskTagService {

    @Override
    public R<?> getUserTagList(Long userId) {
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        QueryWrapper<TaskTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("usage_count");
        
        List<TaskTag> tagList = baseMapper.selectList(wrapper);
        
        return R.ok(tagList);
    }

    @Override
    public R<?> addTag(TaskTag tag) {
        if (tag == null) {
            return R.error(ResultCode.PARAM_ERROR, "标签信息不能为空");
        }
        
        if (tag.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        if (!StringUtils.hasText(tag.getName())) {
            return R.error(ResultCode.PARAM_ERROR, "标签名称不能为空");
        }
        
        // 检查标签名称是否已存在
        QueryWrapper<TaskTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", tag.getUserId());
        wrapper.eq("name", tag.getName());
        
        if (baseMapper.selectCount(wrapper) > 0) {
            return R.error(ResultCode.DATA_ALREADY_EXISTS, "该标签名称已存在");
        }
        
        // 设置默认值
        if (tag.getUsageCount() == null) {
            tag.setUsageCount(0);
        }
        
        if (baseMapper.insert(tag) > 0) {
            return R.ok("添加成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "添加失败");
        }
    }

    @Override
    public R<?> updateTag(TaskTag tag) {
        if (tag == null || tag.getId() == null) {
            return R.error(ResultCode.PARAM_ERROR, "标签ID不能为空");
        }
        
        // 检查标签是否存在
        TaskTag existingTag = baseMapper.selectById(tag.getId());
        if (existingTag == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "标签不存在");
        }
        
        // 检查权限
        if (!existingTag.getUserId().equals(tag.getUserId())) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        // 如果修改了标签名称，检查是否已存在
        if (StringUtils.hasText(tag.getName()) && !tag.getName().equals(existingTag.getName())) {
            QueryWrapper<TaskTag> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", tag.getUserId());
            wrapper.eq("name", tag.getName());
            wrapper.ne("id", tag.getId());
            
            if (baseMapper.selectCount(wrapper) > 0) {
                return R.error(ResultCode.DATA_ALREADY_EXISTS, "该标签名称已存在");
            }
        }
        
        if (baseMapper.updateById(tag) > 0) {
            return R.ok("更新成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "更新失败");
        }
    }

    @Override
    public R<?> deleteTag(Long tagId, Long userId) {
        if (tagId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        // 检查标签是否存在
        TaskTag tag = baseMapper.selectById(tagId);
        if (tag == null) {
            return R.error(ResultCode.DATA_NOT_FOUND, "标签不存在");
        }
        
        // 检查权限
        if (!tag.getUserId().equals(userId)) {
            return R.error(ResultCode.PERMISSION_DENIED, "无权限操作");
        }
        
        if (baseMapper.deleteById(tagId) > 0) {
            return R.ok("删除成功");
        } else {
            return R.error(ResultCode.SYSTEM_ERROR, "删除失败");
        }
    }

    @Override
    public R<?> batchAddTags(Map<String, Object> params) {
        if (params == null) {
            return R.error(ResultCode.PARAM_ERROR, "参数不能为空");
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        List<String> tagNames = (List<String>) params.get("tagNames");
        
        if (userId == null || tagNames == null || tagNames.isEmpty()) {
            return R.error(ResultCode.PARAM_ERROR, "参数不完整");
        }
        
        // 过滤空标签名称
        List<String> validTagNames = new ArrayList<>();
        for (String tagName : tagNames) {
            if (StringUtils.hasText(tagName)) {
                validTagNames.add(tagName.trim());
            }
        }
        
        if (validTagNames.isEmpty()) {
            return R.error(ResultCode.PARAM_ERROR, "标签名称不能为空");
        }
        
        // 查询已存在的标签
        QueryWrapper<TaskTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.in("name", validTagNames);
        
        List<TaskTag> existingTags = baseMapper.selectList(wrapper);
        Map<String, Long> existingTagNameMap = new HashMap<>();
        for (TaskTag tag : existingTags) {
            existingTagNameMap.put(tag.getName(), tag.getId());
        }
        
        // 构建需要插入的标签列表
        List<TaskTag> tagsToInsert = new ArrayList<>();
        for (String tagName : validTagNames) {
            if (!existingTagNameMap.containsKey(tagName)) {
                TaskTag newTag = new TaskTag();
                newTag.setUserId(userId);
                newTag.setName(tagName);
                newTag.setUsageCount(0);
                tagsToInsert.add(newTag);
            }
        }
        
        // 批量插入
        if (!tagsToInsert.isEmpty()) {
            this.saveBatch(tagsToInsert);
            
            // 重新查询以获取完整信息
            wrapper.clear();
            wrapper.eq("user_id", userId);
            wrapper.in("name", validTagNames);
            existingTags = baseMapper.selectList(wrapper);
        }
        
        return R.ok(existingTags);
    }
}