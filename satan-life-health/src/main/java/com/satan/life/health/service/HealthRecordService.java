package com.satan.life.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.health.entity.HealthRecord;
import com.satan.life.common.result.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 健康记录服务接口
 */
public interface HealthRecordService extends IService<HealthRecord> {

    /**
     * 添加健康记录
     * @param healthRecord 健康记录信息
     * @return 添加结果
     */
    R<?> addRecord(HealthRecord healthRecord);

    /**
     * 更新健康记录
     * @param healthRecord 健康记录信息
     * @return 更新结果
     */
    R<?> updateRecord(HealthRecord healthRecord);

    /**
     * 删除健康记录
     * @param id 记录ID
     * @return 删除结果
     */
    R<?> deleteRecord(Long id);

    /**
     * 获取健康记录详情
     * @param id 记录ID
     * @return 记录详情
     */
    R<?> getRecordById(Long id);

    /**
     * 查询用户健康记录列表
     * @param userId 用户ID
     * @param recordType 记录类型（可选）
     * @param startTime 开始时间（可选）
     * @param endTime 结束时间（可选）
     * @return 记录列表
     */
    R<?> getRecordList(Long userId, Integer recordType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取用户健康数据统计
     * @param userId 用户ID
     * @param recordType 记录类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计数据
     */
    R<?> getRecordStatistics(Long userId, Integer recordType, LocalDate startDate, LocalDate endDate);
}