package com.satan.life.asset.controller;

import com.satan.life.asset.entity.Asset;
import com.satan.life.asset.service.AssetService;
import com.satan.life.common.result.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 物品资产控制器
 */
@RestController
@RequestMapping("/api/asset/asset")
public class AssetController {

    @Resource
    private AssetService assetService;

    /**
     * 添加物品资产
     */
    @PostMapping
    public R<?> addAsset(@RequestBody Asset asset) {
        return assetService.addAsset(asset);
    }

    /**
     * 更新物品资产
     */
    @PutMapping
    public R<?> updateAsset(@RequestBody Asset asset) {
        return assetService.updateAsset(asset);
    }

    /**
     * 删除物品资产
     */
    @DeleteMapping("/{id}")
    public R<?> deleteAsset(@PathVariable Long id, @RequestParam Long userId) {
        return assetService.deleteAsset(id, userId);
    }

    /**
     * 获取物品资产详情
     */
    @GetMapping("/{id}")
    public R<?> getAssetDetail(@PathVariable Long id, @RequestParam Long userId) {
        return assetService.getAssetDetail(id, userId);
    }

    /**
     * 查询物品资产列表
     */
    @GetMapping
    public R<?> getAssetList(@RequestParam Long userId,
                             @RequestParam(required = false) Long categoryId,
                             @RequestParam(required = false) Integer status) {
        return assetService.getAssetList(userId, categoryId, status);
    }

    /**
     * 批量删除物品资产
     */
    @DeleteMapping("/batch")
    public R<?> batchDeleteAssets(@RequestBody List<Long> ids, @RequestParam Long userId) {
        return assetService.batchDeleteAssets(ids, userId);
    }

    /**
     * 更新物品资产状态
     */
    @PutMapping("/{id}/status")
    public R<?> updateAssetStatus(@PathVariable Long id,
                                 @RequestParam Long userId,
                                 @RequestParam Integer status) {
        return assetService.updateAssetStatus(id, userId, status);
    }
}