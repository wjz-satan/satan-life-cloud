package com.satan.life.analytics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.analytics.entity.UserTaskAnalysis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户任务完成分析Mapper接口
 */
@Mapper
public interface UserTaskAnalysisMapper extends BaseMapper<UserTaskAnalysis> {
    
    /**
     * 根据用户ID和日期范围查询任务完成分析
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 用户任务完成分析列表
     */
    List<UserTaskAnalysis> selectByUserIdAndDateRange(Long userId, String startDate, String endDate);
    
    /**
     * 查询用户最新的任务完成分析记录
     * @param userId 用户ID
     * @return 最新的任务完成分析记录
     */
    UserTaskAnalysis selectLatestByUserId(Long userId);
}