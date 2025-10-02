package com.satan.life.trip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.trip.entity.TripItem;
import java.util.List;

/**
 * 行程项目服务接口
 */
public interface TripItemService extends IService<TripItem> {
    
    /**
     * 根据行程ID查询行程项目列表
     * @param tripId 行程ID
     * @return 行程项目列表
     */
    List<TripItem> getTripItemListByTripId(Long tripId);
    
    /**
     * 创建行程项目
     * @param tripItem 行程项目对象
     * @return 创建结果
     */
    boolean createTripItem(TripItem tripItem);
    
    /**
     * 更新行程项目
     * @param tripItem 行程项目对象
     * @return 更新结果
     */
    boolean updateTripItem(TripItem tripItem);
    
    /**
     * 删除行程项目
     * @param id 行程项目ID
     * @return 删除结果
     */
    boolean deleteTripItem(Long id);
    
    /**
     * 批量删除行程项目
     * @param ids 行程项目ID列表
     * @return 删除结果
     */
    boolean deleteBatch(List<Long> ids);
    
    /**
     * 根据行程ID批量删除行程项目
     * @param tripId 行程ID
     * @return 删除结果
     */
    boolean deleteByTripId(Long tripId);
}