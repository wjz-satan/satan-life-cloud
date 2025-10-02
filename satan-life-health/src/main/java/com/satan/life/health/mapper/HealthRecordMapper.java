package com.satan.life.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.health.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康记录Mapper接口
 */
@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {

}