package com.satan.life.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.asset.entity.AssetCategory;
import com.satan.life.common.result.R;

import java.util.List;

/**
 * 物品分类服务接口
 */
public interface AssetCategoryService extends IService<AssetCategory> {
    
    /**
     * 添加物品分类
     * @param category 物品分类信息
     * @return 操作结果
     */
    R<?> addCategory(AssetCategory category);
    
    /**
     * 更新物品分类
     * @param category 物品分类信息
     * @return 操作结果
     */
    R<?> updateCategory(AssetCategory category);
    
    /**
     * 删除物品分类
     * @param id 分类ID
     * @return 操作结果
     */
    R<?> deleteCategory(Long id);
    
    /**
     * 获取分类详情
     * @param id 分类ID
     * @return 分类详情
     */
    R<AssetCategory> getCategoryDetail(Long id);
    
    /**
     * 获取所有分类列表（树形结构）
     * @return 分类树形结构
     */
    R<List<AssetCategory>> getCategoryTree();
    
    /**
     * 获取指定父分类下的子分类
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    R<List<AssetCategory>> getSubCategories(Long parentId);
}