package com.satan.life.note.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.satan.life.note.entity.Note;
import com.satan.life.note.mapper.NoteMapper;
import com.satan.life.note.service.NoteService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 笔记服务实现类
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Override
    public R<?> addNote(Note note) {
        if (note == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (note.getUserId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (!StringUtils.hasText(note.getTitle())) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 设置默认值
        if (note.getAccessType() == null) {
            note.setAccessType(1); // 默认私有
        }
        
        if (note.getTop() == null) {
            note.setTop(false); // 默认不置顶
        }
        
        if (note.getReadCount() == null) {
            note.setReadCount(0);
        }
        
        if (baseMapper.insert(note) > 0) {
            return R.success("添加成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> updateNote(Note note) {
        if (note == null || note.getId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查笔记是否存在
        Note existingNote = baseMapper.selectById(note.getId());
        if (existingNote == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!existingNote.getUserId().equals(note.getUserId())) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        if (baseMapper.updateById(note) > 0) {
            return R.success("更新成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> deleteNote(Long noteId, Long userId) {
        if (noteId == null || userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查笔记是否存在
        Note note = baseMapper.selectById(noteId);
        if (note == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!note.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        if (baseMapper.deleteById(noteId) > 0) {
            return R.success("删除成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> getNoteDetail(Long noteId, Long userId) {
        if (noteId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查笔记是否存在
        Note note = baseMapper.selectById(noteId);
        if (note == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查访问权限
        if (note.getAccessType() == 1 && userId != null && !note.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        // 增加阅读量
        if (userId == null || !note.getUserId().equals(userId)) {
            note.setReadCount(note.getReadCount() + 1);
            baseMapper.updateById(note);
        }
        
        // TODO: 获取分类名称和标签列表
        
        return R.success(note);
    }

    @Override
    public R<?> getNoteList(Map<String, Object> params) {
        if (params == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        Long categoryId = params.get("categoryId") != null ? Long.valueOf(params.get("categoryId").toString()) : null;
        Integer page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) : 1;
        Integer pageSize = params.get("pageSize") != null ? Integer.valueOf(params.get("pageSize").toString()) : 10;
        String orderBy = params.get("orderBy") != null ? params.get("orderBy").toString() : "create_time";
        String orderType = params.get("orderType") != null ? params.get("orderType").toString() : "desc";
        
        if (userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        
        // 构建排序
        if ("asc".equals(orderType)) {
            wrapper.orderByAsc(orderBy);
        } else {
            wrapper.orderByDesc(orderBy);
        }
        
        Page<Note> notePage = new Page<>(page, pageSize);
        Page<Note> resultPage = baseMapper.selectPage(notePage, wrapper);
        
        // TODO: 填充分类名称和标签列表
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", resultPage.getTotal());
        result.put("list", resultPage.getRecords());
        
        return R.success(result);
    }

    @Override
    public R<?> searchNotes(Map<String, Object> params) {
        if (params == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        Long userId = params.get("userId") != null ? Long.valueOf(params.get("userId").toString()) : null;
        String keyword = params.get("keyword") != null ? params.get("keyword").toString() : null;
        Integer page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) : 1;
        Integer pageSize = params.get("pageSize") != null ? Integer.valueOf(params.get("pageSize").toString()) : 10;
        
        if (userId == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        if (!StringUtils.hasText(keyword)) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        QueryWrapper<Note> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.and(w -> w.like("title", keyword).or().like("content", keyword));
        wrapper.orderByDesc("create_time");
        
        Page<Note> notePage = new Page<>(page, pageSize);
        Page<Note> resultPage = baseMapper.selectPage(notePage, wrapper);
        
        Map<String, Object> result = new HashMap<>();
        result.put("total", resultPage.getTotal());
        result.put("list", resultPage.getRecords());
        
        return R.success(result);
    }

    @Override
    public R<?> toggleTop(Long noteId, Long userId, Boolean top) {
        if (noteId == null || userId == null || top == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查笔记是否存在
        Note note = baseMapper.selectById(noteId);
        if (note == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!note.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        note.setTop(top);
        
        if (baseMapper.updateById(note) > 0) {
            return R.success("操作成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }

    @Override
    public R<?> updateAccessType(Long noteId, Long userId, Integer accessType) {
        if (noteId == null || userId == null || accessType == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查访问类型是否合法
        if (accessType != 0 && accessType != 1) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }
        
        // 检查笔记是否存在
        Note note = baseMapper.selectById(noteId);
        if (note == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }
        
        // 检查权限
        if (!note.getUserId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }
        
        note.setAccessType(accessType);
        
        if (baseMapper.updateById(note) > 0) {
            return R.success("操作成功");
        } else {
            return R.<Object>error(ResultCode.ERROR);
        }
    }
}