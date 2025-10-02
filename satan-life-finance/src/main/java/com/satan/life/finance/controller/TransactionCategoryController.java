package com.satan.life.finance.controller;

import com.satan.life.finance.entity.TransactionCategory;
import com.satan.life.finance.service.TransactionCategoryService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 交易分类控制器
 */
@RestController
@RequestMapping("/api/finance/category")
public class TransactionCategoryController {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public R<?> getCategoryList(@RequestParam Long userId, @RequestParam Integer type) {
        return transactionCategoryService.getCategoryList(userId, type);
    }

    /**
     * 添加分类
     */
    @PostMapping("/add")
    public R<?> addCategory(@RequestBody TransactionCategory category) {
        return transactionCategoryService.addCategory(category);
    }

    /**
     * 更新分类
     */
    @PutMapping("/update")
    public R<?> updateCategory(@RequestBody TransactionCategory category) {
        return transactionCategoryService.updateCategory(category);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    public R<?> deleteCategory(@RequestBody Map<String, Object> params) {
        Long categoryId = Long.valueOf(params.get("categoryId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        return transactionCategoryService.deleteCategory(categoryId, userId);
    }
}