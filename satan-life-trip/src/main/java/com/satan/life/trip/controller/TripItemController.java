package com.satan.life.trip.controller;

import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import com.satan.life.trip.entity.TripItem;
import com.satan.life.trip.service.TripItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 行程项目控制器
 */
@RestController
@RequestMapping("/api/trip-item")
public class TripItemController {

    @Autowired
    private TripItemService tripItemService;

    /**
     * 获取行程下的所有项目
     * @param tripId 行程ID
     * @return 行程项目列表
     */
    @GetMapping("/list")
    public R getTripItemList(@RequestParam Long tripId) {
        List<TripItem> tripItemList = tripItemService.getTripItemListByTripId(tripId);
        return R.success(tripItemList);
    }

    /**
     * 获取行程项目详情
     * @param id 行程项目ID
     * @return 行程项目详情
     */
    @GetMapping("/{id}")
    public R getTripItemDetail(@PathVariable Long id) {
        TripItem tripItem = tripItemService.getById(id);
        return tripItem != null ? R.success(tripItem) : R.error(ResultCode.BUSINESS_ERROR.getCode(), "行程项目不存在");
    }

    /**
     * 创建行程项目
     * @param tripItem 行程项目对象
     * @return 创建结果
     */
    @PostMapping
    public R createTripItem(@RequestBody TripItem tripItem) {
        boolean result = tripItemService.createTripItem(tripItem);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "创建失败");
    }

    /**
     * 更新行程项目
     * @param tripItem 行程项目对象
     * @return 更新结果
     */
    @PutMapping
    public R updateTripItem(@RequestBody TripItem tripItem) {
        boolean result = tripItemService.updateTripItem(tripItem);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "更新失败");
    }

    /**
     * 删除行程项目
     * @param id 行程项目ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R deleteTripItem(@PathVariable Long id) {
        boolean result = tripItemService.deleteTripItem(id);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }

    /**
     * 批量删除行程项目
     * @param ids 行程项目ID列表
     * @return 删除结果
     */
    @DeleteMapping("/batch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        boolean result = tripItemService.deleteBatch(ids);
        return result ? R.success() : R.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}