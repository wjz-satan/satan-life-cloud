package com.satan.life.note.controller;

import com.satan.life.note.entity.Note;
import com.satan.life.note.service.NoteService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 笔记控制器
 */
@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * 添加笔记
     */
    @PostMapping("/add")
    public R<?> addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

    /**
     * 更新笔记
     */
    @PutMapping("/update")
    public R<?> updateNote(@RequestBody Note note) {
        return noteService.updateNote(note);
    }

    /**
     * 删除笔记
     */
    @DeleteMapping("/delete")
    public R<?> deleteNote(@RequestBody Map<String, Object> params) {
        Long noteId = Long.valueOf(params.get("noteId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        return noteService.deleteNote(noteId, userId);
    }

    /**
     * 获取笔记详情
     */
    @GetMapping("/detail")
    public R<?> getNoteDetail(@RequestParam Long noteId, @RequestParam(required = false) Long userId) {
        return noteService.getNoteDetail(noteId, userId);
    }

    /**
     * 获取笔记列表
     */
    @GetMapping("/list")
    public R<?> getNoteList(@RequestParam Map<String, Object> params) {
        return noteService.getNoteList(params);
    }

    /**
     * 搜索笔记
     */
    @GetMapping("/search")
    public R<?> searchNotes(@RequestParam Map<String, Object> params) {
        return noteService.searchNotes(params);
    }

    /**
     * 置顶/取消置顶笔记
     */
    @PutMapping("/toggleTop")
    public R<?> toggleTop(@RequestBody Map<String, Object> params) {
        Long noteId = Long.valueOf(params.get("noteId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        Boolean top = Boolean.valueOf(params.get("top").toString());
        return noteService.toggleTop(noteId, userId, top);
    }

    /**
     * 更新笔记访问类型
     */
    @PutMapping("/updateAccessType")
    public R<?> updateAccessType(@RequestBody Map<String, Object> params) {
        Long noteId = Long.valueOf(params.get("noteId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        Integer accessType = Integer.valueOf(params.get("accessType").toString());
        return noteService.updateAccessType(noteId, userId, accessType);
    }
}