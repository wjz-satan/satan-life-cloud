package com.satan.life.trip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.trip.entity.Trip;
import java.util.List;

/**
 * 行程服务接口
 */
public interface TripService extends IService<Trip> {
    
    /**
     * 根据用户ID查询行程列表
     * @param userId 用户ID
     * @return 行程列表
     */
    List<Trip> getTripListByUserId(Long userId);
    
    /**
     * 创建行程
     * @param trip 行程对象
     * @return 创建结果
     */
    boolean createTrip(Trip trip);
    
    /**
     * 更新行程
     * @param trip 行程对象
     * @return 更新结果
     */
    boolean updateTrip(Trip trip);
    
    /**
     * 删除行程
     * @param id 行程ID
     * @return 删除结果
     */
    boolean deleteTrip(Long id);
    
    /**
     * 批量删除行程
     * @param ids 行程ID列表
     * @return 删除结果
     */
    boolean deleteBatch(List<Long> ids);
}