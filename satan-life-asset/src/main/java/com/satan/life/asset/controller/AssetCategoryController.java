package com.satan.life.asset.controller;

import com.satan.life.asset.entity.AssetCategory;
import com.satan.life.asset.service.AssetCategoryService;
import com.satan.life.common.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物品分类控制器
 */
@RestController
@RequestMapping("/api/asset/category")
public class AssetCategoryController {

    @Resource
    private AssetCategoryService assetCategoryService;

    /**
     * 添加物品分类
     */
    @PostMapping
    public R<?> addCategory(@RequestBody AssetCategory category) {
        return assetCategoryService.addCategory(category);
    }

    /**
     * 更新物品分类
     */
    @PutMapping
    public R<?> updateCategory(@RequestBody AssetCategory category) {
        return assetCategoryService.updateCategory(category);
    }

    /**
     * 删除物品分类
     */
    @DeleteMapping("/{id}")
    public R<?> deleteCategory(@PathVariable Long id) {
        return assetCategoryService.deleteCategory(id);
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/{id}")
    public R<?> getCategoryDetail(@PathVariable Long id) {
        return assetCategoryService.getCategoryDetail(id);
    }

    /**
     * 获取分类树形结构
     */
    @GetMapping("/tree")
    public R<?> getCategoryTree() {
        return assetCategoryService.getCategoryTree();
    }

    /**
     * 获取子分类列表
     */
    @GetMapping("/sub")
    public R<?> getSubCategories(@RequestParam(required = false) Long parentId) {
        return assetCategoryService.getSubCategories(parentId);
    }
}