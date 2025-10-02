package com.satan.life.analytics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.analytics.entity.UserTaskAnalysis;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户任务完成分析Service接口
 */
public interface UserTaskAnalysisService extends IService<UserTaskAnalysis> {
    
    /**
     * 生成用户任务完成分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的任务完成分析记录
     */
    UserTaskAnalysis generateUserTaskAnalysis(Long userId, LocalDate analysisDate);
    
    /**
     * 获取用户任务完成分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 任务完成分析列表
     */
    List<UserTaskAnalysis> getUserTaskAnalysisList(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 获取用户最新的任务完成分析
     * @param userId 用户ID
     * @return 最新的任务完成分析记录
     */
    UserTaskAnalysis getLatestUserTaskAnalysis(Long userId);
    
    /**
     * 批量生成用户任务完成分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的任务完成分析记录数量
     */
    int batchGenerateUserTaskAnalysis(List<Long> userIds, LocalDate analysisDate);
}