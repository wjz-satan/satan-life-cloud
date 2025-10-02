package com.satan.life.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.health.entity.HealthRecord;
import com.satan.life.health.mapper.HealthRecordMapper;
import com.satan.life.health.service.HealthRecordService;
import com.satan.life.common.result.R;
import com.satan.life.common.result.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 健康记录服务实现类
 */
@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {

    @Resource
    private HealthRecordMapper healthRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> addRecord(HealthRecord healthRecord) {
        // 校验参数
        if (healthRecord == null || healthRecord.getUserId() == null || healthRecord.getRecordType() == null || healthRecord.getValue() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 设置默认值
        if (healthRecord.getRecordTime() == null) {
            healthRecord.setRecordTime(LocalDateTime.now());
        }
        healthRecord.setCreateTime(LocalDateTime.now());
        healthRecord.setUpdateTime(LocalDateTime.now());
        healthRecord.setCreateBy(healthRecord.getUserId());
        healthRecord.setUpdateBy(healthRecord.getUserId());
        healthRecord.setDeleted(0);

        // 保存记录
        int result = healthRecordMapper.insert(healthRecord);
        if (result > 0) {
            return R.success("添加成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> updateRecord(HealthRecord healthRecord) {
        // 校验参数
        if (healthRecord == null || healthRecord.getId() == null || healthRecord.getUserId() == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查记录是否存在
        HealthRecord existRecord = healthRecordMapper.selectById(healthRecord.getId());
        if (existRecord == null || existRecord.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 检查是否有权限修改
        if (!existRecord.getUserId().equals(healthRecord.getUserId())) {
            return R.error(ResultCode.UNAUTHORIZED);
        }

        // 更新记录
        HealthRecord updateRecord = new HealthRecord();
        updateRecord.setId(healthRecord.getId());
        updateRecord.setRecordType(healthRecord.getRecordType());
        updateRecord.setValue(healthRecord.getValue());
        updateRecord.setUnit(healthRecord.getUnit());
        updateRecord.setRemark(healthRecord.getRemark());
        updateRecord.setRecordTime(healthRecord.getRecordTime());
        updateRecord.setUpdateTime(LocalDateTime.now());
        updateRecord.setUpdateBy(healthRecord.getUserId());

        int result = healthRecordMapper.updateById(updateRecord);
        if (result > 0) {
            return R.success("更新成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> deleteRecord(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 检查记录是否存在
        HealthRecord existRecord = healthRecordMapper.selectById(id);
        if (existRecord == null || existRecord.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        // 逻辑删除
        HealthRecord deleteRecord = new HealthRecord();
        deleteRecord.setId(id);
        deleteRecord.setDeleted(1);
        deleteRecord.setUpdateTime(LocalDateTime.now());

        int result = healthRecordMapper.updateById(deleteRecord);
        if (result > 0) {
            return R.success("删除成功");
        }
        return R.error(ResultCode.ERROR);
    }

    @Override
    public R<?> getRecordById(Long id) {
        // 校验参数
        if (id == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 查询记录
        HealthRecord record = healthRecordMapper.selectById(id);
        if (record == null || record.getDeleted() == 1) {
            return R.error(ResultCode.NOT_FOUND);
        }

        return R.success(record);
    }

    @Override
    public R<?> getRecordList(Long userId, Integer recordType, LocalDateTime startTime, LocalDateTime endTime) {
        // 校验参数
        if (userId == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        QueryWrapper<HealthRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("deleted", 0);

        if (recordType != null) {
            wrapper.eq("record_type", recordType);
        }

        if (startTime != null) {
            wrapper.ge("record_time", startTime);
        }

        if (endTime != null) {
            wrapper.le("record_time", endTime);
        }

        // 按记录时间倒序排列
        wrapper.orderByDesc("record_time");

        // 查询记录列表
        List<HealthRecord> recordList = healthRecordMapper.selectList(wrapper);
        return R.success(recordList);
    }

    @Override
    public R<?> getRecordStatistics(Long userId, Integer recordType, LocalDate startDate, LocalDate endDate) {
        // 校验参数
        if (userId == null || recordType == null || startDate == null || endDate == null) {
            return R.error(ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        QueryWrapper<HealthRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("record_type", recordType);
        wrapper.eq("deleted", 0);
        wrapper.ge("record_time", startDate.atStartOfDay());
        wrapper.le("record_time", endDate.atTime(LocalTime.MAX));
        wrapper.orderByAsc("record_time");

        // 查询记录列表
        List<HealthRecord> recordList = healthRecordMapper.selectList(wrapper);

        if (recordList.isEmpty()) {
            return R.success("暂无数据");
        }

        // 计算统计数据
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("count", recordList.size());
        statistics.put("minValue", recordList.stream().mapToDouble(HealthRecord::getValue).min().orElse(0));
        statistics.put("maxValue", recordList.stream().mapToDouble(HealthRecord::getValue).max().orElse(0));
        statistics.put("avgValue", recordList.stream().mapToDouble(HealthRecord::getValue).average().orElse(0));

        // 分组统计（按日期）
        Map<LocalDate, List<HealthRecord>> groupByDate = recordList.stream()
                .collect(Collectors.groupingBy(record -> record.getRecordTime().toLocalDate()));
        statistics.put("groupByDate", groupByDate);

        return R.success(statistics);
    }
}