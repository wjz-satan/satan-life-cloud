package com.satan.life.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.finance.entity.TransactionCategory;
import com.satan.life.common.result.R;

import java.util.List;

/**
 * 交易分类服务接口
 */
public interface TransactionCategoryService extends IService<TransactionCategory> {
    /**
     * 获取用户的交易分类列表
     * @param userId 用户ID
     * @param type 分类类型(1:收入,2:支出)
     * @return 分类列表
     */
    R<?> getCategoryList(Long userId, Integer type);

    /**
     * 添加交易分类
     * @param category 分类信息
     * @return 添加结果
     */
    R<?> addCategory(TransactionCategory category);

    /**
     * 更新交易分类
     * @param category 分类信息
     * @return 更新结果
     */
    R<?> updateCategory(TransactionCategory category);

    /**
     * 删除交易分类
     * @param categoryId 分类ID
     * @param userId 用户ID
     * @return 删除结果
     */
    R<?> deleteCategory(Long categoryId, Long userId);
}