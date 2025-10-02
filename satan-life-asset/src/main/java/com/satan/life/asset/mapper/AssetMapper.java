package com.satan.life.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.asset.entity.Asset;
import org.apache.ibatis.annotations.Mapper;

/**
 * 物品资产Mapper接口
 */
@Mapper
public interface AssetMapper extends BaseMapper<Asset> {
    
}