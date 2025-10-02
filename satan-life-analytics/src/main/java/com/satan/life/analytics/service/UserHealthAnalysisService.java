package com.satan.life.analytics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.analytics.entity.UserHealthAnalysis;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户健康分析Service接口
 */
public interface UserHealthAnalysisService extends IService<UserHealthAnalysis> {
    
    /**
     * 生成用户健康分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的健康分析记录
     */
    UserHealthAnalysis generateUserHealthAnalysis(Long userId, LocalDate analysisDate);
    
    /**
     * 获取用户健康分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康分析列表
     */
    List<UserHealthAnalysis> getUserHealthAnalysisList(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 获取用户最新的健康分析
     * @param userId 用户ID
     * @return 最新的健康分析记录
     */
    UserHealthAnalysis getLatestUserHealthAnalysis(Long userId);
    
    /**
     * 批量生成用户健康分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的健康分析记录数量
     */
    int batchGenerateUserHealthAnalysis(List<Long> userIds, LocalDate analysisDate);
}