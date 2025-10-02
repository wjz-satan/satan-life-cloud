package com.satan.life.analytics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.satan.life.analytics.entity.UserSpendingAnalysis;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 用户消费分析Mapper接口
 */
@Mapper
public interface UserSpendingAnalysisMapper extends BaseMapper<UserSpendingAnalysis> {
    
    /**
     * 根据用户ID和日期范围查询消费分析
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 用户消费分析列表
     */
    List<UserSpendingAnalysis> selectByUserIdAndDateRange(Long userId, String startDate, String endDate);
    
    /**
     * 查询用户最新的消费分析记录
     * @param userId 用户ID
     * @return 最新的消费分析记录
     */
    UserSpendingAnalysis selectLatestByUserId(Long userId);
}