package com.satan.life.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.trip.entity.Trip;
import com.satan.life.trip.mapper.TripMapper;
import com.satan.life.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 行程服务实现类
 */
@Service
public class TripServiceImpl extends ServiceImpl<TripMapper, Trip> implements TripService {

    @Override
    public List<Trip> getTripListByUserId(Long userId) {
        QueryWrapper<Trip> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("update_time");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean createTrip(Trip trip) {
        // 可以添加业务校验逻辑
        return save(trip);
    }

    @Override
    public boolean updateTrip(Trip trip) {
        // 可以添加业务校验逻辑
        return updateById(trip);
    }

    @Override
    public boolean deleteTrip(Long id) {
        // 可以添加业务校验逻辑
        return removeById(id);
    }

    @Override
    public boolean deleteBatch(List<Long> ids) {
        // 可以添加业务校验逻辑
        return removeByIds(ids);
    }
}