package com.satan.life.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.asset.entity.Asset;
import com.satan.life.asset.mapper.AssetMapper;
import com.satan.life.asset.service.AssetService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 物品资产服务实现类
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements AssetService {

    @Resource
    private AssetMapper assetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addAsset(Asset asset) {
        // 校验参数
        if (asset == null || asset.getUserId() == null || asset.getName() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 设置默认值
        if (asset.getStatus() == null) {
            asset.setStatus(1); // 默认使用中
        }
        asset.setCreateTime(LocalDateTime.now());
        asset.setUpdateTime(LocalDateTime.now());
        asset.setCreateBy(asset.getUserId());
        asset.setUpdateBy(asset.getUserId());
        asset.setDeleted(0);

        // 保存物品资产
        int result = assetMapper.insert(asset);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateAsset(Asset asset) {
        // 校验参数
        if (asset == null || asset.getId() == null || asset.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查物品是否存在
        Asset existAsset = assetMapper.selectById(asset.getId());
        if (existAsset == null || existAsset.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限修改
        if (!existAsset.getUserId().equals(asset.getUserId())) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 更新物品资产
        Asset updateAsset = new Asset();
        updateAsset.setId(asset.getId());
        updateAsset.setName(asset.getName());
        updateAsset.setCategoryId(asset.getCategoryId());
        updateAsset.setValue(asset.getValue());
        updateAsset.setPurchaseDate(asset.getPurchaseDate());
        updateAsset.setStatus(asset.getStatus());
        updateAsset.setDescription(asset.getDescription());
        updateAsset.setLocation(asset.getLocation());
        updateAsset.setBrand(asset.getBrand());
        updateAsset.setModel(asset.getModel());
        updateAsset.setSerialNumber(asset.getSerialNumber());
        updateAsset.setWarrantyEndDate(asset.getWarrantyEndDate());
        updateAsset.setUpdateTime(LocalDateTime.now());
        updateAsset.setUpdateBy(asset.getUserId());

        int result = assetMapper.updateById(updateAsset);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteAsset(Long id, Long userId) {
        // 校验参数
        if (id == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查物品是否存在
        Asset asset = assetMapper.selectById(id);
        if (asset == null || asset.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限删除
        if (!asset.getUserId().equals(userId)) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 逻辑删除
        Asset updateAsset = new Asset();
        updateAsset.setId(id);
        updateAsset.setDeleted(1);
        updateAsset.setUpdateTime(LocalDateTime.now());
        updateAsset.setUpdateBy(userId);

        int result = assetMapper.updateById(updateAsset);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<Asset> getAssetDetail(Long id, Long userId) {
        // 校验参数
        if (id == null || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询物品详情
        Asset asset = assetMapper.selectById(id);
        if (asset == null || asset.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限查看
        if (!asset.getUserId().equals(userId)) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        return R.success(asset);
    }

    @Override
    public R<List<Asset>> getAssetList(Long userId, Long categoryId, Integer status) {
        // 校验参数
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        QueryWrapper<Asset> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("deleted", 0);
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("update_time");

        // 查询列表
        List<Asset> assetList = assetMapper.selectList(queryWrapper);
        return R.success(assetList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> batchDeleteAssets(List<Long> ids, Long userId) {
        // 校验参数
        if (ids == null || ids.isEmpty() || userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查所有物品是否属于该用户
        QueryWrapper<Asset> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        queryWrapper.ne("user_id", userId);
        queryWrapper.eq("deleted", 0);
        if (assetMapper.selectCount(queryWrapper) > 0) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 批量逻辑删除
        Asset updateAsset = new Asset();
        updateAsset.setDeleted(1);
        updateAsset.setUpdateTime(LocalDateTime.now());
        updateAsset.setUpdateBy(userId);

        queryWrapper.clear();
        queryWrapper.in("id", ids);
        queryWrapper.eq("deleted", 0);
        int result = assetMapper.update(updateAsset, queryWrapper);
        if (result > 0) {
            return R.success("批量删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateAssetStatus(Long id, Long userId, Integer status) {
        // 校验参数
        if (id == null || userId == null || status == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查物品是否存在
        Asset asset = assetMapper.selectById(id);
        if (asset == null || asset.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限修改
        if (!asset.getUserId().equals(userId)) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 更新状态
        Asset updateAsset = new Asset();
        updateAsset.setId(id);
        updateAsset.setStatus(status);
        updateAsset.setUpdateTime(LocalDateTime.now());
        updateAsset.setUpdateBy(userId);

        int result = assetMapper.updateById(updateAsset);
        if (result > 0) {
            return R.success("状态更新成功");
        }
        return R.error(ResultCode.ERROR);
    }
}