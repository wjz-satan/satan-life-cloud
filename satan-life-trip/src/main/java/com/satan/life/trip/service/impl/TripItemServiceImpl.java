package com.satan.life.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.trip.entity.TripItem;
import com.satan.life.trip.mapper.TripItemMapper;
import com.satan.life.trip.service.TripItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 行程项目服务实现类
 */
@Service
public class TripItemServiceImpl extends ServiceImpl<TripItemMapper, TripItem> implements TripItemService {

    @Override
    public List<TripItem> getTripItemListByTripId(Long tripId) {
        QueryWrapper<TripItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trip_id", tripId);
        queryWrapper.orderByAsc("start_time");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean createTripItem(TripItem tripItem) {
        // 可以添加业务校验逻辑
        return save(tripItem);
    }

    @Override
    public boolean updateTripItem(TripItem tripItem) {
        // 可以添加业务校验逻辑
        return updateById(tripItem);
    }

    @Override
    public boolean deleteTripItem(Long id) {
        // 可以添加业务校验逻辑
        return removeById(id);
    }

    @Override
    public boolean deleteBatch(List<Long> ids) {
        // 可以添加业务校验逻辑
        return removeByIds(ids);
    }

    @Override
    public boolean deleteByTripId(Long tripId) {
        QueryWrapper<TripItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trip_id", tripId);
        return remove(queryWrapper);
    }
}