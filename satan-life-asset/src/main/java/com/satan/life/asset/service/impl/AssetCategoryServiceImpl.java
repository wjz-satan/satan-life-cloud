package com.satan.life.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.asset.entity.AssetCategory;
import com.satan.life.asset.mapper.AssetCategoryMapper;
import com.satan.life.asset.service.AssetCategoryService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 物品分类服务实现类
 */
@Service
public class AssetCategoryServiceImpl extends ServiceImpl<AssetCategoryMapper, AssetCategory> implements AssetCategoryService {

    @Resource
    private AssetCategoryMapper assetCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addCategory(AssetCategory category) {
        // 校验参数
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查分类名称是否已存在
        QueryWrapper<AssetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", category.getName().trim());
        queryWrapper.eq("deleted", 0);
        if (assetCategoryMapper.selectCount(queryWrapper) > 0) {
            return R.error(ResultCode.BUSINESS_ERROR.getCode(), "分类名称已存在");
        }

        // 设置默认值
        if (category.getParentId() == null) {
            category.setParentId(0L); // 根分类
        }
        if (category.getSort() == null) {
            category.setSort(0);
        }
        category.setName(category.getName().trim());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setDeleted(0);

        // 保存分类
        int result = assetCategoryMapper.insert(category);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateCategory(AssetCategory category) {
        // 校验参数
        if (category == null || category.getId() == null || category.getName() == null || category.getName().trim().isEmpty()) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查分类是否存在
        AssetCategory existCategory = assetCategoryMapper.selectById(category.getId());
        if (existCategory == null || existCategory.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查分类名称是否已存在（排除当前分类）
        QueryWrapper<AssetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", category.getName().trim());
        queryWrapper.ne("id", category.getId());
        queryWrapper.eq("deleted", 0);
        if (assetCategoryMapper.selectCount(queryWrapper) > 0) {
            return R.error(ResultCode.BUSINESS_ERROR.getCode(), "分类名称已存在");
        }

        // 检查是否更新为自己的子分类
        if (category.getParentId() != null && category.getParentId().equals(category.getId())) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 更新分类
        AssetCategory updateCategory = new AssetCategory();
        updateCategory.setId(category.getId());
        updateCategory.setName(category.getName().trim());
        updateCategory.setParentId(category.getParentId());
        updateCategory.setIcon(category.getIcon());
        updateCategory.setSort(category.getSort());
        updateCategory.setUpdateTime(LocalDateTime.now());

        int result = assetCategoryMapper.updateById(updateCategory);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteCategory(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查分类是否存在
        AssetCategory category = assetCategoryMapper.selectById(id);
        if (category == null || category.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有子分类
        QueryWrapper<AssetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        queryWrapper.eq("deleted", 0);
        if (assetCategoryMapper.selectCount(queryWrapper) > 0) {
            return R.error(ResultCode.BUSINESS_ERROR.getCode(), "该分类下存在子分类，无法删除");
        }

        // 逻辑删除
        AssetCategory updateCategory = new AssetCategory();
        updateCategory.setId(id);
        updateCategory.setDeleted(1);
        updateCategory.setUpdateTime(LocalDateTime.now());

        int result = assetCategoryMapper.updateById(updateCategory);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<AssetCategory> getCategoryDetail(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询分类详情
        AssetCategory category = assetCategoryMapper.selectById(id);
        if (category == null || category.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        return R.success(category);
    }

    @Override
    public R<List<AssetCategory>> getCategoryTree() {
        // 查询所有分类
        QueryWrapper<AssetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("parent_id", "sort");
        List<AssetCategory> allCategories = assetCategoryMapper.selectList(queryWrapper);

        // 构建树形结构
        List<AssetCategory> tree = buildCategoryTree(allCategories, 0L);
        return R.success(tree);
    }

    @Override
    public R<List<AssetCategory>> getSubCategories(Long parentId) {
        // 默认查询一级分类
        if (parentId == null) {
            parentId = 0L;
        }

        // 查询子分类
        QueryWrapper<AssetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");
        List<AssetCategory> subCategories = assetCategoryMapper.selectList(queryWrapper);
        return R.success(subCategories);
    }

    /**
     * 构建分类树形结构
     * @param allCategories 所有分类
     * @param parentId 父分类ID
     * @return 分类树形结构
     */
    private List<AssetCategory> buildCategoryTree(List<AssetCategory> allCategories, Long parentId) {
        List<AssetCategory> tree = new ArrayList<>();
        Map<Long, List<AssetCategory>> childrenMap = new HashMap<>();

        // 构建子分类映射
        for (AssetCategory category : allCategories) {
            Long pId = category.getParentId();
            if (pId == null) {
                pId = 0L;
            }
            childrenMap.computeIfAbsent(pId, k -> new ArrayList<>()).add(category);
        }

        // 递归构建树形结构
        buildTreeRecursive(tree, childrenMap, parentId);
        return tree;
    }

    /**
     * 递归构建分类树形结构
     * @param tree 当前层树节点
     * @param childrenMap 子分类映射
     * @param parentId 父分类ID
     */
    private void buildTreeRecursive(List<AssetCategory> tree, Map<Long, List<AssetCategory>> childrenMap, Long parentId) {
        List<AssetCategory> children = childrenMap.get(parentId);
        if (children == null || children.isEmpty()) {
            return;
        }

        for (AssetCategory category : children) {
            tree.add(category);
            // 递归添加子分类
            List<AssetCategory> subChildren = new ArrayList<>();
            category.setChildren(subChildren);
            buildTreeRecursive(subChildren, childrenMap, category.getId());
        }
    }
}