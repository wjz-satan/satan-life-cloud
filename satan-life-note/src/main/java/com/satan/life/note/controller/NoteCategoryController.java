package com.satan.life.note.controller;

import com.satan.life.note.entity.NoteCategory;
import com.satan.life.note.service.NoteCategoryService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 笔记分类控制器
 */
@RestController
@RequestMapping("/api/note/category")
public class NoteCategoryController {

    @Autowired
    private NoteCategoryService noteCategoryService;

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public R<?> getCategoryList(@RequestParam Long userId) {
        return noteCategoryService.getCategoryList(userId);
    }

    /**
     * 添加分类
     */
    @PostMapping("/add")
    public R<?> addCategory(@RequestBody NoteCategory category) {
        return noteCategoryService.addCategory(category);
    }

    /**
     * 更新分类
     */
    @PutMapping("/update")
    public R<?> updateCategory(@RequestBody NoteCategory category) {
        return noteCategoryService.updateCategory(category);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    public R<?> deleteCategory(@RequestBody Map<String, Object> params) {
        Long categoryId = Long.valueOf(params.get("categoryId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        return noteCategoryService.deleteCategory(categoryId, userId);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/detail")
    public R<?> getCategoryDetail(@RequestParam Long categoryId, @RequestParam Long userId) {
        return noteCategoryService.getCategoryDetail(categoryId, userId);
    }
}