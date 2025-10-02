package com.satan.life.analytics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.analytics.entity.UserSpendingAnalysis;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户消费分析Service接口
 */
public interface UserSpendingAnalysisService extends IService<UserSpendingAnalysis> {
    
    /**
     * 生成用户消费分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的消费分析记录
     */
    UserSpendingAnalysis generateUserSpendingAnalysis(Long userId, LocalDate analysisDate);
    
    /**
     * 获取用户消费分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 消费分析列表
     */
    List<UserSpendingAnalysis> getUserSpendingAnalysisList(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 获取用户最新的消费分析
     * @param userId 用户ID
     * @return 最新的消费分析记录
     */
    UserSpendingAnalysis getLatestUserSpendingAnalysis(Long userId);
    
    /**
     * 批量生成用户消费分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的消费分析记录数量
     */
    int batchGenerateUserSpendingAnalysis(List<Long> userIds, LocalDate analysisDate);
}