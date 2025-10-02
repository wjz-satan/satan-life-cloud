package com.satan.life.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.finance.entity.TransactionCategory;
import com.satan.life.finance.mapper.TransactionCategoryMapper;
import com.satan.life.finance.service.TransactionCategoryService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易分类服务实现类
 */
@Service
public class TransactionCategoryServiceImpl extends ServiceImpl<TransactionCategoryMapper, TransactionCategory> implements TransactionCategoryService {

    @Resource
    private TransactionCategoryMapper transactionCategoryMapper;

    @Override
    public R<?> getCategoryList(Long userId, Integer type) {
        // 校验参数
        if (userId == null || userId <= 0 || type == null || (type != 1 && type != 2)) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询用户自定义分类和系统默认分类
        QueryWrapper<TransactionCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type)
                .and(i -> i.eq("tenant_id", userId).or().eq("is_default", 1))
                .eq("deleted", 0)
                .orderByAsc("sort");

        List<TransactionCategory> categoryList = transactionCategoryMapper.selectList(wrapper);
        return R.success(categoryList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addCategory(TransactionCategory category) {
        // 校验参数
        if (category == null || category.getTenantId() == null || !StringUtils.hasText(category.getName()) || category.getType() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }

        // 检查分类名称是否已存在
        QueryWrapper<TransactionCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("tenant_id", category.getTenantId())
                .eq("type", category.getType())
                .eq("name", category.getName())
                .eq("deleted", 0);
        if (transactionCategoryMapper.selectCount(wrapper) > 0) {
            return R.<Object>error(501, "分类名称已存在");
        }

        // 设置默认值
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getIsDefault() == null) {
            category.setIsDefault(0);
        }

        // 保存分类
        int result = transactionCategoryMapper.insert(category);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateCategory(TransactionCategory category) {
        // 校验参数
        if (category == null || category.getId() == null || category.getTenantId() == null) {
            return R.<Object>error(ResultCode.PARAM_ERROR);
        }

        // 检查分类是否存在
        TransactionCategory existCategory = transactionCategoryMapper.selectById(category.getId());
        if (existCategory == null || existCategory.getDeleted() == 1) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 检查是否为用户自己的分类
        if (!existCategory.getTenantId().equals(category.getTenantId())) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }

        // 检查是否为系统默认分类
        if (existCategory.getIsDefault() == 1) {
            return R.<Object>error(501, "系统默认分类不能修改");
        }

        // 更新分类信息
        TransactionCategory updateCategory = new TransactionCategory();
        updateCategory.setId(category.getId());
        if (StringUtils.hasText(category.getName())) {
            updateCategory.setName(category.getName());
        }
        if (StringUtils.hasText(category.getIcon())) {
            updateCategory.setIcon(category.getIcon());
        }
        if (StringUtils.hasText(category.getColor())) {
            updateCategory.setColor(category.getColor());
        }
        if (category.getSort() != null) {
            updateCategory.setSort(category.getSort());
        }

        int result = transactionCategoryMapper.updateById(updateCategory);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteCategory(Long categoryId, Long userId) {
        // 校验参数
        if (categoryId == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查分类是否存在
        TransactionCategory category = transactionCategoryMapper.selectById(categoryId);
        if (category == null) {
            return R.<Object>error(ResultCode.NOT_FOUND);
        }

        // 检查是否为用户自己的分类
        if (!category.getTenantId().equals(userId)) {
            return R.<Object>error(ResultCode.FORBIDDEN);
        }

        // 检查是否为系统默认分类
        if (category.getIsDefault() == 1) {
            return R.<Object>error(501, "系统默认分类不能删除");
        }

        // 删除分类（逻辑删除）
        int result = transactionCategoryMapper.deleteById(categoryId);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }
}