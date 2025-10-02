package com.satan.life.analytics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.analytics.entity.UserHealthAnalysis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户健康分析Mapper接口
 */
@Mapper
public interface UserHealthAnalysisMapper extends BaseMapper<UserHealthAnalysis> {
    
    /**
     * 根据用户ID和日期范围查询健康分析
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 用户健康分析列表
     */
    List<UserHealthAnalysis> selectByUserIdAndDateRange(Long userId, String startDate, String endDate);
    
    /**
     * 查询用户最新的健康分析记录
     * @param userId 用户ID
     * @return 最新的健康分析记录
     */
    UserHealthAnalysis selectLatestByUserId(Long userId);
}