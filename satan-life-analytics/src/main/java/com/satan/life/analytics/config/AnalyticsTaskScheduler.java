package com.satan.life.analytics.config;

import com.satan.life.analytics.service.UserHealthAnalysisService;
import com.satan.life.analytics.service.UserSpendingAnalysisService;
import com.satan.life.analytics.service.UserTaskAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据分析定时任务配置
 */
@Configuration
@EnableScheduling
public class AnalyticsTaskScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsTaskScheduler.class);
    
    @Autowired
    private UserSpendingAnalysisService userSpendingAnalysisService;
    
    @Autowired
    private UserHealthAnalysisService userHealthAnalysisService;
    
    @Autowired
    private UserTaskAnalysisService userTaskAnalysisService;
    
    @Value("${analytics.schedule.cron-expression}")
    private String cronExpression;
    
    /**
     * 每天定时执行用户数据分析任务
     */
    @Scheduled(cron = "${analytics.schedule.cron-expression}")
    public void scheduledAnalyticsTask() {
        logger.info("开始执行定时数据分析任务");
        
        // 获取前一天的日期
        LocalDate analysisDate = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        logger.info("分析日期：{}", analysisDate.format(formatter));
        
        try {
            // 模拟获取所有用户ID（实际应该从用户服务获取）
            List<Long> userIds = getAllUserIds();
            
            // 批量生成各类分析报告
            int spendingCount = userSpendingAnalysisService.batchGenerateUserSpendingAnalysis(userIds, analysisDate);
            int healthCount = userHealthAnalysisService.batchGenerateUserHealthAnalysis(userIds, analysisDate);
            int taskCount = userTaskAnalysisService.batchGenerateUserTaskAnalysis(userIds, analysisDate);
            
            logger.info("定时数据分析任务执行完成，共处理用户数：{}", userIds.size());
            logger.info("消费分析报告生成数量：{}", spendingCount);
            logger.info("健康分析报告生成数量：{}", healthCount);
            logger.info("任务分析报告生成数量：{}", taskCount);
            
        } catch (Exception e) {
            logger.error("定时数据分析任务执行失败", e);
        }
    }
    
    /**
     * 模拟获取所有用户ID
     * 实际应该从用户服务获取所有活跃用户ID
     */
    private List<Long> getAllUserIds() {
        // 模拟数据，实际应该调用用户服务获取
        List<Long> userIds = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            userIds.add(i);
        }
        return userIds;
    }
    
    /**
     * 清理过期的分析数据
     * 这里可以根据配置的保留天数定期清理旧数据
     */
    @Scheduled(cron = "0 0 2 1 * ?") // 每月1日凌晨2点执行
    public void cleanupOldData() {
        logger.info("开始清理过期的分析数据");
        
        try {
            // 实际应该实现清理逻辑
            // 根据配置的保留天数，删除超过保留期限的数据
            
            logger.info("过期数据清理完成");
        } catch (Exception e) {
            logger.error("清理过期数据失败", e);
        }
    }
}