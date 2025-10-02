package com.satan.life.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.asset.entity.Asset;
import com.satan.life.common.result.R;

import java.util.List;

/**
 * 物品资产服务接口
 */
public interface AssetService extends IService<Asset> {
    
    /**
     * 添加物品资产
     * @param asset 物品资产信息
     * @return 操作结果
     */
    R<?> addAsset(Asset asset);
    
    /**
     * 更新物品资产
     * @param asset 物品资产信息
     * @return 操作结果
     */
    R<?> updateAsset(Asset asset);
    
    /**
     * 删除物品资产
     * @param id 物品资产ID
     * @param userId 用户ID
     * @return 操作结果
     */
    R<?> deleteAsset(Long id, Long userId);
    
    /**
     * 获取物品资产详情
     * @param id 物品资产ID
     * @param userId 用户ID
     * @return 物品资产详情
     */
    R<Asset> getAssetDetail(Long id, Long userId);
    
    /**
     * 查询用户的物品资产列表
     * @param userId 用户ID
     * @param categoryId 分类ID（可选）
     * @param status 状态（可选）
     * @return 物品资产列表
     */
    R<List<Asset>> getAssetList(Long userId, Long categoryId, Integer status);
    
    /**
     * 批量删除物品资产
     * @param ids 物品资产ID列表
     * @param userId 用户ID
     * @return 操作结果
     */
    R<?> batchDeleteAssets(List<Long> ids, Long userId);
    
    /**
     * 更新物品资产状态
     * @param id 物品资产ID
     * @param userId 用户ID
     * @param status 新状态
     * @return 操作结果
     */
    R<?> updateAssetStatus(Long id, Long userId, Integer status);
}