package com.satan.life.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.satan.life.task.service.TaskTagService;
import com.satan.life.common.result.R;

import java.util.Map;

/**
 * 任务标签控制器
 */
@RestController
@RequestMapping("/api/task/tag")
public class TaskTagController {

    @Autowired
    private TaskTagService taskTagService;

    /**
     * 获取用户标签列表
     */
    @GetMapping("/list")
    public R<?> getUserTagList(@RequestParam Long userId) {
        return taskTagService.getUserTagList(userId);
    }

    /**
     * 添加标签
     */
    @PostMapping
    public R<?> addTag(@RequestBody Map<String, Object> params) {
        return taskTagService.addTag(params);
    }

    /**
     * 更新标签
     */
    @PutMapping
    public R<?> updateTag(@RequestBody Map<String, Object> params) {
        return taskTagService.updateTag(params);
    }

    /**
     * 删除标签
     */
    @DeleteMapping
    public R<?> deleteTag(@RequestParam Long tagId, @RequestParam Long userId) {
        return taskTagService.deleteTag(tagId, userId);
    }

    /**
     * 批量添加标签
     */
    @PostMapping("/batch")
    public R<?> batchAddTags(@RequestBody Map<String, Object> params) {
        return taskTagService.batchAddTags(params);
    }
}