package com.satan.life.trip.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.trip.entity.TripItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 行程项目Mapper接口
 */
@Mapper
public interface TripItemMapper extends BaseMapper<TripItem> {
    // 可以根据需要添加自定义的SQL查询方法
}