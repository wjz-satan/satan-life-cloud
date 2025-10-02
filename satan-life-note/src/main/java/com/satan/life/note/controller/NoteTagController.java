package com.satan.life.note.controller;

import com.satan.life.note.entity.NoteTag;
import com.satan.life.note.service.NoteTagService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 笔记标签控制器
 */
@RestController
@RequestMapping("/api/note/tag")
public class NoteTagController {

    @Autowired
    private NoteTagService noteTagService;

    /**
     * 获取用户标签列表
     */
    @GetMapping("/list")
    public R<?> getUserTagList(@RequestParam Long userId) {
        return noteTagService.getUserTagList(userId);
    }

    /**
     * 添加标签
     */
    @PostMapping("/add")
    public R<?> addTag(@RequestBody NoteTag tag) {
        return noteTagService.addTag(tag);
    }

    /**
     * 更新标签
     */
    @PutMapping("/update")
    public R<?> updateTag(@RequestBody NoteTag tag) {
        return noteTagService.updateTag(tag);
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/delete")
    public R<?> deleteTag(@RequestBody Map<String, Object> params) {
        Long tagId = Long.valueOf(params.get("tagId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        return noteTagService.deleteTag(tagId, userId);
    }

    /**
     * 批量添加标签
     */
    @PostMapping("/batchAdd")
    public R<?> batchAddTags(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        List<String> tagNames = (List<String>) params.get("tagNames");
        return noteTagService.batchAddTags(userId, tagNames);
    }
}