package com.satan.life.note.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.note.entity.Note;
import com.satan.life.common.result.R;
import java.util.Map;

/**
 * 笔记服务接口
 */
public interface NoteService extends IService<Note> {
    
    /**
     * 添加笔记
     */
    R<?> addNote(Note note);
    
    /**
     * 更新笔记
     */
    R<?> updateNote(Note note);
    
    /**
     * 删除笔记
     */
    R<?> deleteNote(Long noteId, Long userId);
    
    /**
     * 获取笔记详情
     */
    R<?> getNoteDetail(Long noteId, Long userId);
    
    /**
     * 获取笔记列表
     */
    R<?> getNoteList(Map<String, Object> params);
    
    /**
     * 搜索笔记
     */
    R<?> searchNotes(Map<String, Object> params);
    
    /**
     * 置顶/取消置顶笔记
     */
    R<?> toggleTop(Long noteId, Long userId, Boolean top);
    
    /**
     * 更新笔记访问类型
     */
    R<?> updateAccessType(Long noteId, Long userId, Integer accessType);
}