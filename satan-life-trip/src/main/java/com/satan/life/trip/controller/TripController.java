package com.satan.life.trip.controller;

import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import com.satan.life.trip.entity.Trip;
import com.satan.life.trip.service.TripService;
import com.satan.life.trip.service.TripItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 行程控制器
 */
@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripItemService tripItemService;

    /**
     * 获取用户的行程列表
     * @param userId 用户ID
     * @return 行程列表
     */
    @GetMapping("/list")
    public R getTripList(@RequestParam Long userId) {
        List<Trip> tripList = tripService.getTripListByUserId(userId);
        return R.success(tripList);
    }

    /**
     * 获取行程详情
     * @param id 行程ID
     * @return 行程详情
     */
    @GetMapping("/{id}")
    public R getTripDetail(@PathVariable Long id) {
        Trip trip = tripService.getById(id);
        return trip != null ? R.success(trip) : R.error(ResultCode.BUSINESS_ERROR.getCode(), "行程不存在");
    }

    /**
     * 创建行程
     * @param trip 行程对象
     * @return 创建结果
     */
    @PostMapping
    public R createTrip(@RequestBody Trip trip) {
        boolean result = tripService.createTrip(trip);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "创建失败");
    }

    /**
     * 更新行程
     * @param trip 行程对象
     * @return 更新结果
     */
    @PutMapping
    public R updateTrip(@RequestBody Trip trip) {
        boolean result = tripService.updateTrip(trip);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "更新失败");
    }

    /**
     * 删除行程
     * @param id 行程ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R deleteTrip(@PathVariable Long id) {
        // 删除行程前，先删除行程下的所有项目
        tripItemService.deleteByTripId(id);
        boolean result = tripService.deleteTrip(id);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }

    /**
     * 批量删除行程
     * @param ids 行程ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        // 批量删除行程前，先删除每个行程下的所有项目
        for (Long id : ids) {
            tripItemService.deleteByTripId(id);
        }
        boolean result = tripService.deleteBatch(ids);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}