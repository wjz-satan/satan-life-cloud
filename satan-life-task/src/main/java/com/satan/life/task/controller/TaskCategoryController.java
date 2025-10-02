package com.satan.life.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.satan.life.task.service.TaskCategoryService;
import com.satan.life.common.result.R;

import java.util.Map;

/**
 * 任务分类控制器
 */
@RestController
@RequestMapping("/api/task/category")
public class TaskCategoryController {

    @Autowired
    private TaskCategoryService taskCategoryService;

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public R<?> getCategoryList(@RequestParam Long userId) {
        return taskCategoryService.getCategoryList(userId);
    }

    /**
     * 添加分类
     */
    @PostMapping
    public R<?> addCategory(@RequestBody Map<String, Object> params) {
        return taskCategoryService.addCategory(params);
    }

    /**
     * 更新分类
     */
    @PutMapping
    public R<?> updateCategory(@RequestBody Map<String, Object> params) {
        return taskCategoryService.updateCategory(params);
    }

    /**
     * 删除分类
     */
    @DeleteMapping
    public R<?> deleteCategory(@RequestParam Long categoryId, @RequestParam Long userId) {
        return taskCategoryService.deleteCategory(categoryId, userId);
    }

    /**
     * 获取分类详情
     */
    @GetMapping
    public R<?> getCategoryDetail(@RequestParam Long categoryId, @RequestParam Long userId) {
        return taskCategoryService.getCategoryDetail(categoryId, userId);
    }
}