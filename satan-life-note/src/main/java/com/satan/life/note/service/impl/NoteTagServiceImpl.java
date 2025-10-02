package com.satan.life.note.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.satan.life.note.entity.NoteTag;
import com.satan.life.note.mapper.NoteTagMapper;
import com.satan.life.note.service.NoteTagService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 笔记标签服务实现类
 */
@Service
public class NoteTagServiceImpl extends ServiceImpl<NoteTagMapper, NoteTag> implements NoteTagService {

    @Override
    public R<?> getUserTagList(Long userId) {
        if (userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        QueryWrapper<NoteTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("usage_count");
        wrapper.orderByDesc("create_time");
        
        List<NoteTag> tags = baseMapper.selectList(wrapper);
        return R.success(tags);
    }

    @Override
    public R<?> addTag(NoteTag tag) {
        if (tag == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (tag.getUserId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (!StringUtils.hasText(tag.getName())) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查标签名称是否已存在
        QueryWrapper<NoteTag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", tag.getUserId());
        wrapper.eq("name", tag.getName());
        
        if (baseMapper.selectCount(wrapper) > 0) {
            return R.<Object>error(ResultCode.BUSINESS_ERROR);
        }
        
        // 设置默认使用次数
        if (tag.getUsageCount() == null) {
            tag.setUsageCount(0);
        }
        
        if (baseMapper.insert(tag) > 0) {
            return R.success("添加成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> updateTag(NoteTag tag) {
        if (tag == null || tag.getId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查标签是否存在
        NoteTag existingTag = baseMapper.selectById(tag.getId());
        if (existingTag == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!existingTag.getUserId().equals(tag.getUserId())) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        // 检查标签名称是否已存在
        if (StringUtils.hasText(tag.getName())) {
            QueryWrapper<NoteTag> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", tag.getUserId());
            wrapper.eq("name", tag.getName());
            wrapper.ne("id", tag.getId());
            
            if (baseMapper.selectCount(wrapper) > 0) {
                return R.<Object>error(ResultCode.BUSINESS_ERROR);
            }
        }
        
        if (baseMapper.updateById(tag) > 0) {
            return R.success("更新成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> deleteTag(Long tagId, Long userId) {
        if (tagId == null || userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查标签是否存在
        NoteTag tag = baseMapper.selectById(tagId);
        if (tag == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!tag.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        // TODO: 检查标签是否被笔记使用，如果有则不允许删除或需要先更新相关笔记
        
        if (baseMapper.deleteById(tagId) > 0) {
            return R.success("删除成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> batchAddTags(Long userId, List<String> tagNames) {
        if (userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (tagNames == null || tagNames.isEmpty()) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        List<NoteTag> tagsToAdd = new ArrayList<>();
        List<String> existingTagNames = new ArrayList<>();
        
        // 检查哪些标签已存在
        for (String tagName : tagNames) {
            if (!StringUtils.hasText(tagName)) {
                continue;
            }
            
            QueryWrapper<NoteTag> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("name", tagName);
            
            if (baseMapper.selectCount(wrapper) > 0) {
                existingTagNames.add(tagName);
            } else {
                NoteTag tag = new NoteTag();
                tag.setUserId(userId);
                tag.setName(tagName);
                tag.setUsageCount(0);
                tagsToAdd.add(tag);
            }
        }
        
        // 添加新标签
        if (!tagsToAdd.isEmpty()) {
            this.saveBatch(tagsToAdd);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("addedCount", tagsToAdd.size());
        result.put("existingTags", existingTagNames);
        
        return R.success(result);
    }
}