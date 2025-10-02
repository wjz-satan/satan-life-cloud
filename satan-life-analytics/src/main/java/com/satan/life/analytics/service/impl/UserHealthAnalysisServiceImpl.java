package com.satan.life.analytics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.satan.life.analytics.entity.UserHealthAnalysis;
import com.satan.life.analytics.mapper.UserHealthAnalysisMapper;
import com.satan.life.analytics.service.UserHealthAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 用户健康分析Service实现类
 */
@Service
public class UserHealthAnalysisServiceImpl extends ServiceImpl<UserHealthAnalysisMapper, UserHealthAnalysis> implements UserHealthAnalysisService {
    
    private static final Logger log = LoggerFactory.getLogger(UserHealthAnalysisServiceImpl.class);
    
    @Autowired
    private UserHealthAnalysisMapper userHealthAnalysisMapper;
    
    @Override
    public UserHealthAnalysis generateUserHealthAnalysis(Long userId, LocalDate analysisDate) {
        // 这里实现健康分析生成逻辑
        // 1. 从健康服务获取用户的健康数据
        // 2. 计算平均睡眠时间
        // 3. 计算平均活动时长
        // 4. 计算健康评分
        // 5. 生成健康建议
        
        UserHealthAnalysis analysis = new UserHealthAnalysis();
        analysis.setUserId(userId);
        analysis.setAnalysisDate(analysisDate);
        
        // 模拟数据
        analysis.setAvgSleepDuration(BigDecimal.valueOf(7.5));
        analysis.setAvgActivityMinutes(45);
        
        // 计算健康评分
        int healthScore = calculateHealthScore(analysis.getAvgSleepDuration(), analysis.getAvgActivityMinutes());
        analysis.setHealthScore(healthScore);
        
        // 生成健康建议
        String healthSuggestions = generateHealthSuggestions(healthScore, analysis.getAvgSleepDuration(), analysis.getAvgActivityMinutes());
        analysis.setHealthSuggestions(healthSuggestions);
        
        // 保存分析结果
        save(analysis);
        
        return analysis;
    }
    
    private int calculateHealthScore(BigDecimal avgSleepDuration, Integer avgActivityMinutes) {
        // 简单的健康评分算法
        int score = 0;
        
        // 睡眠评分（7-9小时最佳）
        if (avgSleepDuration.compareTo(BigDecimal.valueOf(7)) >= 0 && avgSleepDuration.compareTo(BigDecimal.valueOf(9)) <= 0) {
            score += 50;
        } else if (avgSleepDuration.compareTo(BigDecimal.valueOf(6)) >= 0 && avgSleepDuration.compareTo(BigDecimal.valueOf(10)) <= 0) {
            score += 35;
        } else {
            score += 20;
        }
        
        // 活动评分（每天至少30分钟）
        if (avgActivityMinutes >= 60) {
            score += 50;
        } else if (avgActivityMinutes >= 30) {
            score += 40;
        } else if (avgActivityMinutes >= 15) {
            score += 25;
        } else {
            score += 10;
        }
        
        return score;
    }
    
    private String generateHealthSuggestions(int healthScore, BigDecimal avgSleepDuration, Integer avgActivityMinutes) {
        StringBuilder suggestions = new StringBuilder();
        
        if (healthScore >= 80) {
            suggestions.append("您的健康状况良好，继续保持！\n");
        } else if (healthScore >= 60) {
            suggestions.append("您的健康状况不错，但还有改进空间。\n");
        } else {
            suggestions.append("建议您关注健康状况，适当调整生活习惯。\n");
        }
        
        // 睡眠建议
        if (avgSleepDuration.compareTo(BigDecimal.valueOf(7)) < 0) {
            suggestions.append("建议增加睡眠时间，每天保持7-9小时睡眠。\n");
        } else if (avgSleepDuration.compareTo(BigDecimal.valueOf(9)) > 0) {
            suggestions.append("睡眠时长偏长，建议适当调整作息时间。\n");
        }
        
        // 活动建议
        if (avgActivityMinutes < 30) {
            suggestions.append("建议增加每日活动量，每天至少进行30分钟中等强度运动。\n");
        } else if (avgActivityMinutes < 60) {
            suggestions.append("如果能增加活动时间到60分钟，对健康会更有益。\n");
        }
        
        return suggestions.toString();
    }
    
    @Override
    public List<UserHealthAnalysis> getUserHealthAnalysisList(Long userId, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return userHealthAnalysisMapper.selectByUserIdAndDateRange(
                userId, 
                startDate.format(formatter), 
                endDate.format(formatter)
        );
    }
    
    @Override
    public UserHealthAnalysis getLatestUserHealthAnalysis(Long userId) {
        return userHealthAnalysisMapper.selectLatestByUserId(userId);
    }
    
    @Override
    public int batchGenerateUserHealthAnalysis(List<Long> userIds, LocalDate analysisDate) {
        int count = 0;
        for (Long userId : userIds) {
            try {
                generateUserHealthAnalysis(userId, analysisDate);
                count++;
            } catch (Exception e) {
                log.error("Failed to generate health analysis for user {}", userId, e);
            }
        }
        return count;
    }
}