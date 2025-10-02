package com.satan.life.health.controller;

import com.satan.life.health.entity.HealthRecord;
import com.satan.life.health.service.HealthRecordService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 健康记录控制器
 */
@RestController
@RequestMapping("/api/health")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    /**
     * 添加健康记录
     */
    @PostMapping("/{userId}/record")
    public R<?> addRecord(@PathVariable Long userId, @RequestBody HealthRecord healthRecord) {
        healthRecord.setUserId(userId);
        return healthRecordService.addRecord(healthRecord);
    }

    /**
     * 更新健康记录
     */
    @PutMapping("/{userId}/record")
    public R<?> updateRecord(@PathVariable Long userId, @RequestBody HealthRecord healthRecord) {
        healthRecord.setUserId(userId);
        return healthRecordService.updateRecord(healthRecord);
    }

    /**
     * 删除健康记录
     */
    @DeleteMapping("/{userId}/record/{id}")
    public R<?> deleteRecord(@PathVariable Long userId, @PathVariable Long id) {
        return healthRecordService.deleteRecord(id);
    }

    /**
     * 获取健康记录详情
     */
    @GetMapping("/{userId}/record/{id}")
    public R<?> getRecordById(@PathVariable Long userId, @PathVariable Long id) {
        return healthRecordService.getRecordById(id);
    }

    /**
     * 查询健康记录列表
     */
    @GetMapping("/{userId}/records")
    public R<?> getRecordList(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer recordType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return healthRecordService.getRecordList(userId, recordType, startTime, endTime);
    }

    /**
     * 获取健康数据统计
     */
    @GetMapping("/{userId}/statistics")
    public R<?> getRecordStatistics(
            @PathVariable Long userId,
            @RequestParam Integer recordType,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return healthRecordService.getRecordStatistics(userId, recordType, startDate, endDate);
    }
}